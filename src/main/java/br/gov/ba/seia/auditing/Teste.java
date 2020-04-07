package br.gov.ba.seia.auditing;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Emerson Santos (emerson.santos@prodeb.ba.gov.br)
 *
 */
@Entity
public class Teste extends BaseEntity {
	private static final long serialVersionUID = -1763870513381628373L;
	@Id
    @GeneratedValue
    private Integer id;
    private String name;

    public Teste() {
        super();
    }

    public Teste(String name, String content) {
        this.name = name;
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

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' + '}';
    }
}