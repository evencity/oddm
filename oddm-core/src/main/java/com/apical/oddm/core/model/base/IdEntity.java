package com.apical.oddm.core.model.base;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * Oracle需要每个Entity独立定义id的SEQUCENCE时，不继承于本类而改为实现一个Idable的接口。
 * 
 * @author 
 */
@MappedSuperclass
public abstract class IdEntity {

	protected Integer id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public IdEntity(Integer id) {
		this.id = id;
	}

	public IdEntity() {
	}
	
	/*“assigned”主键由外部程序负责生成，在 save() 之前指定一个。
	“hilo”通过hi/lo 算法实现的主键生成机制，需要额外的数据库表或字段提供高位值来源。
	“seqhilo”与hilo 类似，通过hi/lo 算法实现的主键生成机制，需要数据库中的 Sequence，适用于支持 Sequence 的数据库，如Oracle。
	“increment”主键按数值顺序递增（老师用的是这个）。此方式的实现机制为在当前应用实例中维持一个变量，以保存着当前的最大值，之后每次需要生成主键的时候将此值加1作为主键。这种方式可能产生的问题是：不能在集群下使用。
	“identity”采用数据库提供的主键生成机制。如DB2、SQL Server、MySQL 中的主键生成机制。
	“sequence”采用数据库提供的 sequence 机制生成主键。如 Oralce 中的Sequence。
	“native”由 Hibernate 根据使用的数据库自行判断采用 identity、hilo、sequence 其中一种作为主键生成方式。
	“uuid.hex”由 Hibernate 基于128 位 UUID 算法 生成16 进制数值（编码后以长度32 的字符串表示）作为主键。
	“uuid.string”与uuid.hex 类似，只是生成的主键未进行编码（长度16），不能应用在 PostgreSQL 数据库中。
	“foreign”使用另外一个相关联的对象的标识符作为主键。
*/
}
