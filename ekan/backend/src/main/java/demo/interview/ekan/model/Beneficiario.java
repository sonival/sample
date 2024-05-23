package demo.interview.ekan.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import demo.interview.ekan.dto.BeneficiarioDTO;
import demo.interview.ekan.dto.DocumentoDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "beneficiario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Beneficiario implements Serializable {
    public Beneficiario(String name, String telefone, LocalDate dtaNascimento) {
        super();
        this.dataNascimento = dtaNascimento;
        this.nome = name;
        this.telefone = telefone;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id long id;

    @NonNull
    @Column(nullable = false)
    private String nome;

    @NonNull
    @Column(nullable = false)
    private String telefone;

    @NonNull
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @CreatedDate
    @Column(name = "data_inclusao")
    @CreationTimestamp(source = SourceType.DB)
    private LocalDateTime dataInclusao;

    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    // @JoinColumn(name = "id", referencedColumnName = "beneficiario_id")
    @OneToMany(mappedBy = "beneficiario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos = new ArrayList<>();

    @Override
    public String toString() {
        return "Beneficiario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", dataInclusao=" + dataInclusao +
                ", dataAtualizacao=" + dataAtualizacao +
                '}';
    }

    @PrePersist
    protected void onCreate() {

        dataInclusao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {

        dataAtualizacao = LocalDateTime.now();
    }

    public BeneficiarioDTO parseBeneficiarioDTO() {
        BeneficiarioDTO resp = new BeneficiarioDTO();
        resp.setDtaNascimento(this.dataNascimento);
        resp.setNome(this.nome);
        resp.setId(this.getId());
        resp.setTelefone(this.telefone);
        resp.setDocumentos(new ArrayList<DocumentoDTO>());
        if (!this.getDocumentos().isEmpty()) {
            this.getDocumentos().forEach(f -> {
                resp.getDocumentos().add(f.parDocumentoDTO());
            });
        }
        return resp;
    }
}
