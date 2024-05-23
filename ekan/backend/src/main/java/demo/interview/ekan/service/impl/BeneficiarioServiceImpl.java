package demo.interview.ekan.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.interview.ekan.model.Beneficiario;
import demo.interview.ekan.model.Documento;
import demo.interview.ekan.repository.BeneficiarioRepository;
import demo.interview.ekan.repository.DocumentoRepository;
import demo.interview.ekan.service.IBeneficiarioService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class BeneficiarioServiceImpl implements IBeneficiarioService {

    @Autowired
    private BeneficiarioRepository repository;

    @Autowired
    private DocumentoRepository documentoRepository;

  
    @Override
    public Beneficiario getByID(Long id) {
        Optional<Beneficiario> beneficario = repository.findById(id);

        if (beneficario.isPresent()) {
            Beneficiario resp = beneficario.get();
            List<Documento> docs = documentoRepository.getAllDocumentsByBeneficiarioId(resp.getId());
            if (docs != null && !docs.isEmpty()) {
                if (resp.getDocumentos() == null) {

                    resp.setDocumentos(new ArrayList<Documento>());
                }
                docs.forEach(d -> {

                    resp.getDocumentos()
                            .add(Documento.builder().descricao(d.getDescricao()).dataAtualizacao(d.getDataAtualizacao())
                                    .dataInclusao(d.getDataInclusao()).tipoDocumento(d.getTipoDocumento()).build());
                });

            }
            return resp;
        }
        throw new EntityNotFoundException();
    }

    @Override
    public Beneficiario getByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Beneficiario> getAll() {
        // TODO Auto-generated method stub
        return repository.findAll();
    }

    @Override
    public Beneficiario add(Beneficiario beneficiario) {
        Beneficiario novoBeneficiario = Beneficiario.builder().nome(beneficiario.getNome())
                .telefone(beneficiario.getTelefone()).dataNascimento(beneficiario.getDataNascimento()).build();
        Beneficiario beneficarioSave = repository.saveAndFlush(novoBeneficiario);

        if (beneficarioSave != null && !beneficiario.getDocumentos().isEmpty()) {
            beneficiario.getDocumentos().forEach(el -> {

                Documento doc = Documento.builder().beneficiario(beneficarioSave).dataAtualizacao(LocalDateTime.now())
                        .dataAtualizacao(LocalDateTime.now()).tipoDocumento(el.getTipoDocumento())
                        .descricao(el.getDescricao()).build();
                documentoRepository.saveAndFlush(doc);
            });

        }
        Beneficiario resp = getByID(beneficarioSave.getId());
        return resp;
    }

    @Override
    public void removeAll() {
        repository.deleteAll();
    }

    @Override
    public void removeById(long id) throws Exception  {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Nao foi possivel excluir id " + id);
        }

    }


    @Override
    public Beneficiario updatBeneficiario(Beneficiario beneficiario) throws Exception {
        Optional<Beneficiario> b = repository.findById(beneficiario.getId());
        if(b.isPresent()){
            Beneficiario resp = b.get();
            resp.builder().nome(beneficiario.getNome()).telefone(beneficiario.getTelefone()).dataNascimento(beneficiario.getDataNascimento()).build();
            repository.save(resp);
            return resp;
        }else{
            throw new Exception("Beneficiario nao localizado");
        }
\
    }

}
