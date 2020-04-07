package br.gov.ba.seia.auditing;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * @author Emerson Santos (emerson.santos@prodeb.ba.gov.br)
 *
 */
@Entity
public class File extends BaseEntity {
	private static final long serialVersionUID = 3286062149287275274L;
	@Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String content;
    @OneToOne
    @JsonSerialize(using = CustomEntitySerializer.class)/**Retorna apenas o id da classe referenciada ao ser serializado**/
    private Teste teste;

    /**
	 * @return the teste
	 */
	public Teste getTeste() {
		return teste;
	}

	/**
	 * @param teste the teste to set
	 */
	public void setTeste(Teste teste) {
		this.teste = teste;
	}

	public File() {
        super();
    }

    public File(String name, String content, Teste teste) {
        this.name = name;
        this.content = content;
        this.teste = teste;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}