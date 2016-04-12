package br.edu.ifnmg.avaliar.logic;

import br.edu.ifnmg.avaliar.util.exception.BusinessException;
import br.edu.ifnmg.avaliar.util.exception.SystemException;
import java.util.List;

public interface GenericLogic<E> {
    
    E save(E entity) throws BusinessException, SystemException;
    void delete(E entity) throws BusinessException, SystemException;
    E refresh(E entity) throws BusinessException, SystemException;
    List<E> find(E entity) throws BusinessException, SystemException;
    
    
}
