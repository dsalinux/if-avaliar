package br.edu.ifnmg.avaliar.dao;

import br.edu.ifnmg.avaliar.entity.Categoria;
import br.edu.ifnmg.avaliar.entity.Questao;
import br.edu.ifnmg.avaliar.entity.Questionario;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class QuestaoDAO extends HibernateGenericDAO<Questao, Integer>{

    public List<Questao> buscarQuestao(Questionario questionario, Categoria categoria){
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("questionario", questionario));
        criteria.add(Restrictions.eq("categoria", categoria));
        criteria.addOrder(Order.asc("ordem"));
        return criteria.list();
    }
    
}
