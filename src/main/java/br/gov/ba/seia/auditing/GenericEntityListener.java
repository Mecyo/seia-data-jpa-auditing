package br.gov.ba.seia.auditing;

import static br.gov.ba.seia.auditing.Action.DELETED;
import static br.gov.ba.seia.auditing.Action.INSERTED;
import static br.gov.ba.seia.auditing.Action.UPDATED;
import static javax.transaction.Transactional.TxType.MANDATORY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;

import org.hibernate.ReplicationMode;
import org.hibernate.Session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

/**
 * @author Emerson Santos (emerson.santos@prodeb.ba.gov.br)
 *
 * @param <T>
 */
public class GenericEntityListener<T> {
	private final String NEW_VALUE = "newValue";
	private final String OLD_VALUE = "oldValue";
	private final String[] propertiesExcludeJson = new String[] { "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate" };

	@PrePersist
	public void posPersist(T target) {
		perform(createLog(target));
	}

	@PreUpdate
	public void preUpdate(T target) {
		perform(updateLog(target));
	}

	@PreRemove
	public void preRemove(T target) {
		perform(deleteLog(target));
	}

	@Transactional(MANDATORY)
	private void perform(LogAuditoria<T> log) {
		EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
		entityManager.persist(log);
	}

	private LogAuditoria<T> createLog(T target) {
		String json = LogAuditoriaUtils.objectToJson(target, propertiesExcludeJson);
		return new LogAuditoria<T>(target.getClass().getName(), getId(target), null, json, INSERTED,
				"http://localhost:4200/files", "127.0.0.1");
	}

	private LogAuditoria<T> updateLog(T target) {
		Map<String, String> differences = getDifferences(target);
		return new LogAuditoria<T>(target.getClass().getName(), getId(target), differences.get(OLD_VALUE),
				differences.get(NEW_VALUE), UPDATED, "http://localhost:4200/files", "127.0.0.1");
	}

	private LogAuditoria<T> deleteLog(T target) {
		Map<String, String> differences = getDifferences(target);
		return new LogAuditoria<T>(target.getClass().getName(), getId(target), differences.get(OLD_VALUE),
				differences.get(NEW_VALUE), DELETED, "http://localhost:4200/files", "127.0.0.1");
	}

	private Integer getId(T target) {
		try {
			return (Integer) target.getClass().getMethod("getId").invoke(target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getDifferences(T target) {
		Map<String, String> mapDifferences = new HashMap<String, String>();
		EntityManager em = BeanUtil.getBean(EntityManager.class);

		Session session = em.unwrap(Session.class);
		session.evict(target);
		T oldTarget = (T) em.find(target.getClass(), getId(target));

		ObjectMapper jackson = new ObjectMapper();

		JsonNode beforeNode;
		JsonNode afterNode;
		try {
			String jsonOldTarget = LogAuditoriaUtils.objectToJson(oldTarget, propertiesExcludeJson);
			String jsonTarget = LogAuditoriaUtils.objectToJson(target, propertiesExcludeJson);
			beforeNode = jackson.readTree(jsonOldTarget);
			afterNode = jackson.readTree(jsonTarget);
			JsonNode patchNode = com.flipkart.zjsonpatch.JsonDiff.asJson(beforeNode, afterNode);

			List<String> listPaths = new ArrayList<String>();

			JsonObject newChanges = new JsonObject();
			JsonObject oldChanges = new JsonObject();
			for (JsonNode jsonNode : patchNode) {
				String pathName = jsonNode.get("path").textValue().replace("/", "");
				listPaths.add(pathName);
				newChanges.addProperty(pathName, jsonNode.get("value").asText());

				oldChanges.addProperty(pathName, beforeNode.findValue(pathName).asText());
			}

			mapDifferences.put(OLD_VALUE, oldChanges.toString());
			mapDifferences.put(NEW_VALUE, newChanges.toString());

			session.replicate(target, ReplicationMode.OVERWRITE);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mapDifferences;
	}
}
