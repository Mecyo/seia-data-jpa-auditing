package br.gov.ba.seia.auditing;

import static br.gov.ba.seia.auditing.enums.Action.DELETED;
import static br.gov.ba.seia.auditing.enums.Action.INSERTED;
import static br.gov.ba.seia.auditing.enums.Action.UPDATED;
import static javax.transaction.Transactional.TxType.MANDATORY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;

import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import br.gov.ba.seia.auditing.model.LogAuditoria;
import br.gov.ba.seia.auditing.utils.LogAuditoriaUtils;

/**
 * @author Emerson Santos (emerson.santos@prodeb.ba.gov.br)
 *
 * @param <T>
 */
public class GenericEntityListener<T> implements ApplicationContextAware {

	private final String NEW_VALUE = "newValue";
	private final String OLD_VALUE = "oldValue";
	
    private ApplicationContext context = null;

    public ApplicationContext getApplicationContext() {
        return this.context;
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.context = ctx;
    }
    
	@Resource
	private AuditorAwareInformations auditorAware;
	
	@Resource
	private Jackson2ObjectMapperBuilder mappingJackson2Object;

	
	@PrePersist
	public void posPersist(T target) {
		perform(createLog(target));
	}

	/**
	 * @param target
	 */
	@PreUpdate
	public void preUpdate(T target) {
		perform(updateLog(target));
	}

	/**
	 * @param target
	 */
	@PreRemove
	public void preRemove(T target) {
		perform(deleteLog(target));
	}

	/**
	 * @param log
	 */
	@Transactional(MANDATORY)
	private void perform(LogAuditoria<T> log) {
		EntityManager entityManager = this.context.getBean(EntityManager.class);
		entityManager.persist(log);
	}

	/**
	 * @param target
	 * @return
	 */
	private LogAuditoria<T> createLog(T target) {
		String json = LogAuditoriaUtils.objectToJson(target, mappingJackson2Object);
		return new LogAuditoria<T>(target.getClass().getName(), null, null, json, INSERTED,
				auditorAware.getClientHost(), auditorAware.getClientIpAddress());
	}

	/**
	 * @param target
	 * @return
	 */
	private LogAuditoria<T> updateLog(T target) {
		Map<String, String> differences = getDifferences(target);
		return new LogAuditoria<T>(target.getClass().getName(), getId(target), differences.get(OLD_VALUE),
				differences.get(NEW_VALUE), UPDATED, auditorAware.getClientHost(), auditorAware.getClientIpAddress());
	}

	/**
	 * @param target
	 * @return
	 */
	private LogAuditoria<T> deleteLog(T target) {
		Map<String, String> differences = getDifferences(target);
		return new LogAuditoria<T>(target.getClass().getName(), getId(target), differences.get(OLD_VALUE),
				differences.get(NEW_VALUE), DELETED, auditorAware.getClientHost(), auditorAware.getClientIpAddress());
	}

	/**
	 * @param target
	 * @return
	 */
	private Integer getId(T target) {
		try {
			return (Integer) target.getClass().getMethod("getId").invoke(target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param target
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> getDifferences(T target) {
		Map<String, String> mapDifferences = new HashMap<String, String>();
		EntityManager em = this.context.getBean(EntityManager.class);

		Session session = em.unwrap(Session.class);
		session.evict(target);
		T oldTarget = (T) em.find(target.getClass(), getId(target));

		ObjectMapper jackson = new ObjectMapper();

		JsonNode beforeNode;
		JsonNode afterNode;
		try {
			String jsonOldTarget = LogAuditoriaUtils.objectToJson(oldTarget, mappingJackson2Object);
			String jsonTarget = LogAuditoriaUtils.objectToJson(target, mappingJackson2Object);
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
		}

		return mapDifferences;
	}
}
