package demo.interview.ekan.model;

import java.time.LocalDateTime;

import demo.interview.ekan.dto.DocumentoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Table(name ="documento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Documento {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id long id;
    private String tipoDocumento;
    private String descricao;
    private LocalDateTime dataInclusao;
    private LocalDateTime dataAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiario_id")
    private Beneficiario beneficiario;

    @PrePersist
    protected void onCreate() {

        dataInclusao = LocalDateTime.now();
        dataAtualizacao= LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
       
        dataAtualizacao = LocalDateTime.now();
    }
    public DocumentoDTO parDocumentoDTO(){
        DocumentoDTO resp = new DocumentoDTO();
        resp.setDescricao(this.descricao);
        resp.setTipoDocumento(this.tipoDocumento);
        return resp;
    }
}
