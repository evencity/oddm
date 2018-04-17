package com.apical.oddm.core.dao.produce;

import org.springframework.stereotype.Repository;

import com.apical.oddm.core.dao.basedao.BaseDaoImpl;
import com.apical.oddm.core.model.produce.ManufacturePackage;

@Repository("manufacturePackageDao")
public class ManufacturePackageDaoImpl extends BaseDaoImpl<ManufacturePackage> implements ManufacturePackageDaoI {

}
