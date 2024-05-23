package demo.interview.ekan.dto;

import demo.interview.ekan.model.Documento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentoDTO {
    private String tipoDocumento;
    private String descricao;
    

    public Documento parseToDocumento(){
        Documento doc =  new Documento();
        doc.setDescricao(this.descricao);
        doc.setTipoDocumento(this.tipoDocumento);
        return doc;
    }
}
