package br.gov.ba.seia.auditing;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


/**
 * @author Emerson Santos (emerson.santos@prodeb.ba.gov.br)
 *
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class LogAuditoria<T> {
    @Id
    @GeneratedValue
    private Integer ideLogAuditoria;
	
	private String ownerType;
	
	private Integer ownerId;
	
	private String oldValue;
	
	private String newValue;
	
	@Enumerated(STRING)
    private Action operacao;
	
	@CreatedDate
    @Temporal(TIMESTAMP)
	private Date dataOperacao;
	
	private String route;
	
	private String ip;

    @CreatedBy
    private String modifiedBy;

    public LogAuditoria() {
    }

	/**
	 * @param ownerType
	 * @param ownerId
	 * @param oldValue
	 * @param newValue
	 * @param operacao
	 * @param dataOperacao
	 * @param route
	 * @param ip
	 * @param modifiedBy
	 */
	public LogAuditoria(String ownerType, Integer ownerId, String oldValue, String newValue, Action operacao, String route, String ip) {
		super();
		this.ownerType = ownerType;
		this.ownerId = ownerId;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.operacao = operacao;
		this.route = route;
		this.ip = ip;
	}

	/**
	 * @return the ideLogAuditoria
	 */
	public Integer getIdeLogAuditoria() {
		return ideLogAuditoria;
	}

	/**
	 * @param ideLogAuditoria the ideLogAuditoria to set
	 */
	public void setIdeLogAuditoria(Integer ideLogAuditoria) {
		this.ideLogAuditoria = ideLogAuditoria;
	}

	/**
	 * @return the ownerType
	 */
	public String getOwnerType() {
		return ownerType;
	}

	/**
	 * @param ownerType the ownerType to set
	 */
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	/**
	 * @return the ownerId
	 */
	public Integer getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the oldValue
	 */
	public String getOldValue() {
		return oldValue;
	}

	/**
	 * @param oldValue the oldValue to set
	 */
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	/**
	 * @return the newValue
	 */
	public String getNewValue() {
		return newValue;
	}

	/**
	 * @param newValue the newValue to set
	 */
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	/**
	 * @return the operacao
	 */
	public Action getOperacao() {
		return operacao;
	}

	/**
	 * @param operacao the operacao to set
	 */
	public void setOperacao(Action operacao) {
		this.operacao = operacao;
	}

	/**
	 * @return the dataOperacao
	 */
	public Date getDataOperacao() {
		return dataOperacao;
	}

	/**
	 * @param dataOperacao the dataOperacao to set
	 */
	public void setDataOperacao(Date dataOperacao) {
		this.dataOperacao = dataOperacao;
	}

	/**
	 * @return the route
	 */
	public String getRoute() {
		return route;
	}

	/**
	 * @param route the route to set
	 */
	public void setRoute(String route) {
		this.route = route;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}