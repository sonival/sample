package demo.interview.ekan.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import demo.interview.ekan.model.Beneficiario;
import demo.interview.ekan.model.Documento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BeneficiarioDTO {
    private String nome;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private  LocalDate dtaNascimento;


    private String telefone;
    
    private Long Id;

    private List<DocumentoDTO> documentos;
    
    public Beneficiario parseToBenefiario(){
        Beneficiario newBeneficiario = new Beneficiario();
        newBeneficiario.setDataNascimento(this.dtaNascimento);
        newBeneficiario.setNome(this.nome);
        newBeneficiario.setTelefone(this.telefone);
        List<Documento> docs =  new ArrayList<Documento>(); 
        if( this.documentos!=null &&  !this.documentos.isEmpty()){
            this.documentos.forEach(doc -> {
                docs.add(doc.parseToDocumento());
            });
            newBeneficiario.setDocumentos(docs);
        }
        return newBeneficiario;
    }
}
