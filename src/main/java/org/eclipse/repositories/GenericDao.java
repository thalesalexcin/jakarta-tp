package org.eclipse.repositories;

import java.util.List;

// 5 méthodes : insert, modif, supp, findAll, findId
// Model : classe modèle
// PK : type de la clé primaire
public interface GenericDao<Model, PK> {
	List<Model> findAll();

	Model findById(PK id);

	Model save(Model model);

	Model update(Model model);

	boolean remove(PK id);
}
// Toutes les classes DAO implémentent GenericDao