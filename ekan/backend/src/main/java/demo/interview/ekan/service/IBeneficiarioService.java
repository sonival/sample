package demo.interview.ekan.service;

import java.util.List;

import demo.interview.ekan.model.Beneficiario;

public interface IBeneficiarioService {
    Beneficiario getByID(Long id);
    Beneficiario getByName(String name);
    Beneficiario add(Beneficiario beneficiario);
    List<Beneficiario> getAll();
    void removeAll();
    void removeById(long id) throws Exception ;
    Beneficiario updatBeneficiario(Beneficiario beneficiario) throws Exception;



}
