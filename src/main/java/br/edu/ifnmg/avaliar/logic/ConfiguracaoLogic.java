package br.edu.ifnmg.avaliar.logic;

import br.edu.ifnmg.avaliar.dao.ConfiguracaoDAO;
import br.edu.ifnmg.avaliar.entity.Configuracao;

public class ConfiguracaoLogic {
    
    private ConfiguracaoDAO dao = new ConfiguracaoDAO();
    
    public Configuracao salvar(Configuracao entity){
        if(entity.getId() == null){
            entity.setId(1);
        }
        return dao.save(entity);
    }
    
    public Configuracao getConfiguracao(){
        Configuracao configuracao = dao.findById(1);
        if(configuracao == null){
            configuracao = new Configuracao();
            configuracao.setId(1);
        }
        return configuracao;
    }
    
}
