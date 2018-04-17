package com.apical.oddm.facade.manufacture.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import com.apical.oddm.application.produce.ManufactureAlterServiceI;
import com.apical.oddm.application.produce.ManufacturePackageTitleServiceI;
import com.apical.oddm.application.produce.ManufactureServiceI;
import com.apical.oddm.application.produce.ManufactureUnreadServiceI;
import com.apical.oddm.application.sys.UserServiceI;
import com.apical.oddm.core.constant.CrudConst;
import com.apical.oddm.core.model.page.Pager;
import com.apical.oddm.core.model.page.SystemContext;
import com.apical.oddm.core.model.produce.Manufacture;
import com.apical.oddm.core.model.produce.ManufactureAlt;
import com.apical.oddm.core.model.produce.ManufactureFitting;
import com.apical.oddm.core.model.produce.ManufactureOs;
import com.apical.oddm.core.model.produce.ManufacturePackage;
import com.apical.oddm.core.model.produce.ManufacturePackageTitle;
import com.apical.oddm.core.model.produce.ManufactureShell;
import com.apical.oddm.core.model.sys.User;
import com.apical.oddm.facade.base.command.PageCommand;
import com.apical.oddm.facade.base.dto.BasePage;
import com.apical.oddm.facade.manufacture.ManufactureAltFacade;
import com.apical.oddm.facade.manufacture.command.ManufactureCommand;
import com.apical.oddm.facade.manufacture.command.ManufactureFittingCommand;
import com.apical.oddm.facade.manufacture.command.ManufactureOsCommand;
import com.apical.oddm.facade.manufacture.command.ManufacturePackageCommand;
import com.apical.oddm.facade.manufacture.command.ManufactureShellCommand;
import com.apical.oddm.facade.manufacture.dto.ManufactureAltDTO;
import com.apical.oddm.facade.pool.ThreadPoolBiz;
import com.apical.oddm.facade.util.TimeUtil;
@Component("manufactureAltFacade")
public class ManufactureAltFacadeImpl implements ManufactureAltFacade{

	private static final Logger log = LoggerFactory.getLogger(ManufactureAltFacadeImpl.class);

	private static FastDateFormat dayFmat = FastDateFormat.getInstance("yyyy-MM-dd");
	
	@Autowired 
	private ManufactureAlterServiceI manufactureAlterService;
	
	@Autowired 
	private ManufactureServiceI manufactureServiceI;
	
	@Autowired 
	private ManufacturePackageTitleServiceI manufacturePackageTitleService;
	
	@Autowired
	private UserServiceI userService;
	
	@Autowired
	private ManufactureUnreadServiceI manufactureUnreadService;
	
	@Override
	public BasePage<ManufactureAltDTO> dataGrid(Integer id,PageCommand pageCommand) {
		SystemContext.setPageOffset(pageCommand.getPage());
		SystemContext.setPageSize(pageCommand.getRows());
		SystemContext.setOrder(pageCommand.getOrder());
		SystemContext.setSort("t."+pageCommand.getSort());
		
		BasePage<ManufactureAltDTO> basePage = new BasePage<ManufactureAltDTO>();
		if(id != null) {
			Pager<ManufactureAlt> dataGrid = manufactureAlterService.dataGrid(id);
			if(dataGrid != null && dataGrid.getSize() > 0){
				List<ManufactureAlt> rows = dataGrid.getRows();
				List<ManufactureAltDTO> manufactureAltDTOs = new ArrayList<ManufactureAltDTO>();
				if(rows != null && rows.size() > 0){
					for(ManufactureAlt manufactureAlt : rows){
						ManufactureAltDTO manufactureAltDTO = new ManufactureAltDTO();
						BeanUtils.copyProperties(manufactureAlt, manufactureAltDTO);
						if(manufactureAlt.getTimestamp() != null){
							manufactureAltDTO.setTimestamp(TimeUtil.timestampToStringDetaile(manufactureAlt.getTimestamp()));;
						}
						manufactureAltDTOs.add(manufactureAltDTO);
					}
					basePage.setRows(manufactureAltDTOs);
					basePage.setTotal(dataGrid.getSize());
				}
			}
		}
		return basePage;
	}


	@Override
	public void addMftUnreadInfo(String orderNo, int currUserId, int mftId) {
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				List<User> users = null;
				try {
					users = userService.listUsersWithMftResource();
					// 批量添加未读信息
					manufactureUnreadService.addManufactureUnreadBatch(currUserId, users, mftId);
				} catch (Exception e) {
					log.error("批量添加指导书未读信息错误,指导书id:" + mftId, e);
				}
			}
		});
	}
	/*public static void main(String[] args) {
		String ss = "picture/produce/2017/0testmanuf/(配件_2017-01-17_17:24:25_467)08.jpg";
		System.out.println(ss.substring(ss.lastIndexOf("/")+1));
	}*/
	
	@Override
	public void editAllRecord(Manufacture manufactureBefore, ManufactureCommand manufactureCommand, Integer currUserId1, String username) {
		if (manufactureBefore == null) {
			return;
		}
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				ManufactureAlt alt1 = null;
				String currUserId = currUserId1+"";
				// String userId = null;;
				String orderNo = null;
				AtomicInteger i = new AtomicInteger(0);// 统计变成项的多少，更新订单为未读
				try {
					// userId = orderInfo.getSellerId()!=null?orderInfo.getSellerId()+"":null;
					orderNo = manufactureBefore.getOrderNo();
					alt1 = new ManufactureAlt(new Manufacture(manufactureBefore.getId()), username);
				} catch (Exception e) {
					log.error(""+orderNo, e);
					return;
				}
				// 主表信息变更记录
				try {
					Class<? extends ManufactureCommand> cl = manufactureCommand.getClass();
					Method[] methods = cl.getMethods();
					for (Method m : methods) {
						for (Annotation a : m.getAnnotations()) {
							if (a instanceof Description) {
								compareString(manufactureBefore.getClass().getMethod(m.getName(), new Class[0]).invoke(manufactureBefore, new Object[0]),
										m.invoke(manufactureCommand, new Object[0]), ((Description) a).value(), alt1, currUserId, orderNo, i);
							}
						}
					}
				} catch (Exception e) {
					log.error("计算包装工艺指导书变更记录错误"+orderNo, e);
				}
				try {
					// 软件信息
					if (manufactureCommand.getManufactureOsCommand() != null) {
						Set<ManufactureOs> osSet = manufactureBefore.getOrderMftOs();
						ManufactureOs os = osSet.iterator().next();
						ManufactureOsCommand manufactureOsCommand = manufactureCommand.getManufactureOsCommand();
						Class<? extends ManufactureOsCommand> cl = manufactureOsCommand.getClass();
						Method[] methods = cl.getMethods();
						for (Method m : methods) {
							for (Annotation a : m.getAnnotations()) {
								if (a instanceof Description) {
									compareString(os.getClass().getMethod(m.getName(), new Class[0]).invoke(os, new Object[0]),
											m.invoke(manufactureOsCommand, new Object[0]), ((Description) a).value(), alt1, currUserId, orderNo, i);
								}
							}
						}
					}
				} catch (Exception e) {
					log.error("计算包装工艺指导书os变更记录错误"+orderNo, e);
				}
				try {
					// 包装工艺指导书外壳
					Set<ManufactureShell> beforeSet = manufactureBefore.getOrderMftShells();//
					if (manufactureCommand.getManufactureShellCommands() != null && !manufactureCommand.getManufactureShellCommands().isEmpty()) {
						List<ManufactureShellCommand> afterSet = manufactureCommand.getManufactureShellCommands();
						if (beforeSet == null || beforeSet.isEmpty()) {// 原有数据为空，则全部是增加的
							for (ManufactureShellCommand c : afterSet) {
								String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
								compareString("", "名称："+c.getName()+"；图片："+path+"；颜色："+c.getColor()+"；喷漆工艺："+c.getCraft()+ "；丝印："+c.getSilk()+ "；备注："+c.getDescription(), "外壳", alt1, currUserId, orderNo, i);
							}
						} else {
							Map<Integer, ManufactureShell> tempMap = new HashMap<>();
							for (ManufactureShell before : beforeSet) {
								tempMap.put(before.getId(), before);
							}
							for (ManufactureShellCommand c : afterSet) {
								if (c.getId() == null) {// 增加
									String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
									compareString("", "名称："+c.getName()+"；图片："+path+"；颜色："+c.getColor()+"；喷漆工艺："+c.getCraft()+ "；丝印："+c.getSilk()+ "；备注："+c.getDescription(), "外壳", alt1, currUserId, orderNo, i);
								} else {// 删除或者修改
									if (tempMap.get(c.getId()) != null) {// 修改操作
										ManufactureShell h = tempMap.get(c.getId());
										Class<? extends ManufactureShellCommand> cl = c.getClass();
										Method[] methods = cl.getMethods();
										for (Method m : methods) {
											for (Annotation a : m.getAnnotations()) {
												if (a instanceof Description) {
													if ("图片".equals(((Description) a).value())) {
														String before = "";
														if (h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]) != null) {
															before = (h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0])+"").substring(c.getPictPaths().lastIndexOf("/")+1);
														}
														// System.err.println("丝印2::::"+m.invoke(c,new Object[0]));
														String after = "";
														if (m.invoke(c, new Object[0]) != null) {
															after = (m.invoke(c, new Object[0])+"").substring(c.getPictPaths().lastIndexOf("/")+1);
														}
														compareString(before, after, "外壳"+c.getName()+"-"+((Description) a).value(), alt1, currUserId, orderNo, i);
													} else {
														compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), "外壳-"
																+ c.getName()+"-"+((Description) a).value(), alt1, currUserId, orderNo, i);
													}
												}
											}
										}
										tempMap.remove(c.getId());// 移除掉修改的，剩下的就应该删除操作
									} else {// 增加，多人操作可能会导致：A先查出，B删除，A再插入。
										String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
										compareString("", "名称："+c.getName()+"；图片："+path+"；颜色："+c.getColor()+"；喷漆工艺："+c.getCraft()+ "；丝印："+c.getSilk()+ "；备注："+c.getDescription(), "外壳", alt1, currUserId, orderNo, i);
									}
								}
							}
							for (ManufactureShell c : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
								String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
								compareString("名称："+c.getName()+"；图片："+path+"；颜色："+c.getColor()+"；喷漆工艺："+c.getCraft()+ "；丝印："+c.getSilk()+ "；备注："+c.getDescription(), "", "外壳", alt1, currUserId, orderNo, i);
							}
						}
					} else{//避免删光，没有变更记录
						if (beforeSet != null && !beforeSet.isEmpty()) {
							for (ManufactureShell c : beforeSet) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
								String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
								compareString("名称："+c.getName()+"；图片："+path+"；颜色："+c.getColor()+"；喷漆工艺："+c.getCraft()+ "；丝印："+c.getSilk()+ "；备注："+c.getDescription(), "", "外壳", alt1, currUserId, orderNo, i);
							}
						}
					}
				} catch (Exception e) {
					log.error("包装工艺指导书外壳变更记录错误"+orderNo, e);
				}
				try {
					// 包装工艺指导书配件
					Set<ManufactureFitting> beforeSet = manufactureBefore.getOrderMftFittings();//
					if (manufactureCommand.getManufactureFittingCommands() != null && !manufactureCommand.getManufactureFittingCommands().isEmpty()) {
						List<ManufactureFittingCommand> afterSet = manufactureCommand.getManufactureFittingCommands();
						if (beforeSet == null || beforeSet.isEmpty()) {// 原有数据为空，则全部是增加的
							for (ManufactureFittingCommand c : afterSet) {
								String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
								compareString("", "名称："+c.getName()+"；图片："+path+"；型号/规格："+c.getSpecification()+"；包装袋："+c.getPackBag()+ "；标贴："+c.getLabel()+ "；备注："+c.getDescription(), "配件", alt1, currUserId, orderNo, i);
							}
						} else {
							Map<Integer, ManufactureFitting> tempMap = new HashMap<>();
							for (ManufactureFitting before : beforeSet) {
								tempMap.put(before.getId(), before);
							}
							for (ManufactureFittingCommand c : afterSet) {
								if (c.getId() == null) {// 增加
									String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
									compareString("", "名称："+c.getName()+"；图片："+path+"；型号/规格："+c.getSpecification()+"；包装袋："+c.getPackBag()+ "；标贴："+c.getLabel()+ "；备注："+c.getDescription(), "配件", alt1, currUserId, orderNo, i);
								} else {// 删除或者修改
									if (tempMap.get(c.getId()) != null) {// 修改操作
										ManufactureFitting h = tempMap.get(c.getId());
										Class<? extends ManufactureFittingCommand> cl = c.getClass();
										Method[] methods = cl.getMethods();
										for (Method m : methods) {
											for (Annotation a : m.getAnnotations()) {
												if (a instanceof Description) {
													if ("图片".equals(((Description) a).value())) {
														String before = "";
														if (h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]) != null) {
															before = (h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0])+"").substring(c.getPictPaths().lastIndexOf("/")+1);
														}
														// System.err.println("丝印2::::"+m.invoke(c,new Object[0]));
														String after = "";
														if (m.invoke(c, new Object[0]) != null) {
															after = (m.invoke(c, new Object[0])+"").substring(c.getPictPaths().lastIndexOf("/")+1);
														}
														compareString(before, after, "配件-"+c.getName()+"-"+((Description) a).value(), alt1, currUserId, orderNo, i);
													} else {
														compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), "配件-"+c.getName()+"-"+((Description) a).value(), alt1, currUserId, orderNo, i);
													}
												}
											}
										}
										tempMap.remove(c.getId());// 移除掉修改的，剩下的就应该删除操作
									} else {// 增加，多人操作可能会导致：A先查出，B删除，A再插入。
										String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
										compareString("", "名称："+c.getName()+"；图片："+path+"；型号/规格："+c.getSpecification()+"；包装袋："+c.getPackBag()+ "；标贴："+c.getLabel()+ "；备注："+c.getDescription(), "配件", alt1, currUserId, orderNo, i);
									}
								}
							}
							for (ManufactureFitting c : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
								String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
								compareString("名称："+c.getName()+"；图片："+path+"；型号/规格："+c.getSpecification()+"；包装袋："+c.getPackBag()+ "；标贴："+c.getLabel()+ "；备注："+c.getDescription(), "", "配件", alt1, currUserId, orderNo, i);
							}
						}
					} else{//避免删光，没有变更记录
						if (beforeSet != null && !beforeSet.isEmpty()) {
							for (ManufactureFitting c : beforeSet) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
								String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
								compareString("名称："+c.getName()+"；图片："+path+"；型号/规格："+c.getSpecification()+"；包装袋："+c.getPackBag()+ "；标贴："+c.getLabel()+ "；备注："+c.getDescription(), "", "配件", alt1, currUserId, orderNo, i);
							}
						}
					}
				} catch (Exception e) {
					log.error("包装工艺指导书配件变更记录错误"+orderNo, e);
				}
				try {
					// 包装工艺指导书包材及包装
					List<ManufacturePackageTitle> listTitleAll = manufacturePackageTitleService.getListAll();
					Map<Integer, String> titleMap = new HashMap<>();
					for (ManufacturePackageTitle t : listTitleAll) {
						titleMap.put(t.getId(), t.getName());
					}
					Set<ManufacturePackage> beforeSet = manufactureBefore.getOrderMftPackages();//
					if (manufactureCommand.getManufacturePackageCommands() != null && !manufactureCommand.getManufacturePackageCommands().isEmpty()) {
						List<ManufacturePackageCommand> afterSet = manufactureCommand.getManufacturePackageCommands();
						if (beforeSet == null || beforeSet.isEmpty()) {// 原有数据为空，则全部是增加的
							for (ManufacturePackageCommand c : afterSet) {
								String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
								compareString("", "标题："+c.getPictTitle()+"；图片："+path+"；描述："+c.getDescription(), "包材及包装-"+titleMap.get(c.getTitleId()), alt1, currUserId, orderNo, i);
							}
						} else {
							Map<Integer, ManufacturePackage> tempMap = new HashMap<>();
							for (ManufacturePackage before : beforeSet) {
								tempMap.put(before.getId(), before);
							}
							for (ManufacturePackageCommand c : afterSet) {
								if (c.getId() == null) {// 增加
									String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
									compareString("", "标题："+c.getPictTitle()+"；图片："+path+"；描述："+c.getDescription(), "包材及包装-"+titleMap.get(c.getTitleId()), alt1, currUserId, orderNo, i);
								} else {// 删除或者修改
									if (tempMap.get(c.getId()) != null) {// 修改操作
										ManufacturePackage h = tempMap.get(c.getId());
										Class<? extends ManufacturePackageCommand> cl = c.getClass();
										Method[] methods = cl.getMethods();
										for (Method m : methods) {
											for (Annotation a : m.getAnnotations()) {
												if (a instanceof Description) {
													String item = c.getPictTitle()==null?"":"-"+c.getPictTitle();
													if ("图片".equals(((Description) a).value())) {
														String before = "";
														if (h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]) != null) {
															before = (h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0])+"").substring(c.getPictPaths().lastIndexOf("/")+1);
														}
														String after = "";
														if (m.invoke(c, new Object[0]) != null) {
															after = (m.invoke(c, new Object[0])+"").substring(c.getPictPaths().lastIndexOf("/")+1);
														}
														compareString(before, after, "包材及包装-"+titleMap.get(c.getTitleId())+item+"-"+((Description) a).value(), alt1, currUserId, orderNo, i);
													} else {
														compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), "包材及包装-"+titleMap.get(c.getTitleId())+item+"-"+((Description) a).value(), alt1, currUserId, orderNo, i);
													}
												}
											}
										}
										tempMap.remove(c.getId());// 移除掉修改的，剩下的就应该删除操作
									} else {// 增加，多人操作可能会导致：A先查出，B删除，A再插入。
										String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
										compareString("", "标题："+c.getPictTitle()+"；图片："+path+"；描述："+c.getDescription(), "包材及包装-"+titleMap.get(c.getTitleId()), alt1, currUserId, orderNo, i);
									}
								}
							}
							for (ManufacturePackage c : tempMap.values()) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
								String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
								compareString("标题："+c.getPictTitle()+"；图片："+path+"；描述："+c.getDescription(), "", "包材及包装-"+titleMap.get(c.getTitleId()), alt1, currUserId, orderNo, i);
							}
						}
					} else{//避免删光，没有变更记录
						if (beforeSet != null && !beforeSet.isEmpty()) {
							for (ManufacturePackage c : beforeSet) {// 删除操作，级联删除起作用的前提是关联的集合对象不能又一次指向新的引用，必须在原有的集合里操作新增、删除、清空元素，像上面的setXXX(null)的方法等是起步到级联删除作用的，大概是Hibernate自认自己原生的集合对象吧。自己New的放进行级联删除无效！
								String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
								compareString("标题："+c.getPictTitle()+"；图片："+path+"；描述："+c.getDescription(), "", "包材及包装-"+titleMap.get(c.getTitleId()), alt1, currUserId, orderNo, i);
							}
						}
					}
				} catch (Exception e) {
					log.error("包装工艺指导书包材及包装变更记录错误"+orderNo, e);
				}
				if (i.get() > 0) {
					//更新未读及时间戳
					manufactureServiceI.updateUpdateTime(manufactureBefore.getId());
					addMftUnreadInfo(orderNo, currUserId1, manufactureBefore.getId());
				}
			}
		});
	}
	/**
	 * 比较量字符串
	 * @param before 变更前
	 * @param after 变更后
	 * @param item 变更项
	 * @param alt
	 * @param currUserId 当前登录用户
	 * @param sellerId 要发消息给对应的用户
	 * @param i 统计更改的几项，为0则没有更改
	 */
	private void compareString(Object before, Object after, String item, ManufactureAlt alt, String currUserId, String orderNo,
			AtomicInteger i) {
		// System.err.println("beforebeforebefore: "+before+"\t ,after:"+after);
		String changeBe = null;
		String changeAf = null;
		// bug注意：1、null
		if (before instanceof Date) {// 格式化日期后再判断
			changeBe = dayFmat.format(before);
		} else if (before != null) {// null+"" 会变成 "null",小心
			changeBe = StringUtils.stripToNull(before+"");// 会转成""
			if (changeBe != null)
				changeBe = changeBe.replaceAll("\r|\n", "");
		}
		if (after instanceof Date) {// 格式化日期后再判断
			changeAf = dayFmat.format(after);
		} else if (after != null) {
			changeAf = StringUtils.stripToNull(after+""); // 会转成""
			if (changeAf != null)
				changeAf = changeAf.replaceAll("\r|\n", "");
		}
		// bug注意：2、换行符
		if (StringUtils.equalsIgnoreCase(changeBe, changeAf)) {
			// x do nothing
			// System.err.println("do nothing "+item);
			return;
		}
		alt = new ManufactureAlt(alt.getManufacture(), alt.getOperator());
		alt.setAlteritem(item);
		if (changeBe == null && changeAf != null) {
			// 增
			alt.setBeforeInfo(null);
			alt.setAfterInfo(changeAf);
			alt.setOperateType(CrudConst.add);
		} else if (changeBe != null && changeAf == null) {
			// 删
			alt.setBeforeInfo(changeBe);
			alt.setAfterInfo(null);
			alt.setOperateType(CrudConst.delete);
		} else {
			// 改
			alt.setBeforeInfo(changeBe);
			alt.setAfterInfo(changeAf);
			alt.setOperateType(CrudConst.update);
		}
		try {
			i.incrementAndGet();
			manufactureAlterService.add(alt);
		} catch (Exception e) {
			log.error("变更记录错误："+orderNo, e);
		}
	}

	@Override
	public void editShellRecord(ManufactureShell mftShellBefore,
			ManufactureShellCommand c, Integer currUserId1, String currUserName) {
		if (mftShellBefore == null) return;
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					String currUserId = currUserId1 +"";
					AtomicInteger i = new AtomicInteger(0);// 统计变成项的多少，更新订单为未读
					ManufactureAlt alt1 = new ManufactureAlt(new Manufacture(c.getManufactureId()), currUserName);
					ManufactureShell h = mftShellBefore;
					Class<? extends ManufactureShellCommand> cl = c.getClass();
					Method[] methods = cl.getMethods();
					for (Method m : methods) {
						for (Annotation a : m.getAnnotations()) {
							if (a instanceof Description) {
								if ("图片".equals(((Description) a).value())) {
									String before = "";
									if (h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]) != null) {
										before = (h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0])+"").substring(c.getPictPaths().lastIndexOf("/")+1);
									}
									String after = "";
									if (m.invoke(c, new Object[0]) != null) {
										after = (m.invoke(c, new Object[0])+"").substring(c.getPictPaths().lastIndexOf("/")+1);
									}
									compareString(before, after, "外壳"+c.getName()+"-"+((Description) a).value(), alt1, currUserId, c.getOrderNo(), i);
								} else {
									compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), "外壳-"
											+ c.getName()+"-"+((Description) a).value(), alt1, currUserId, c.getOrderNo(), i);
								}
							}
						}
					}
					if (i.get() > 0) {
						//更新未读及时间戳
						manufactureServiceI.updateUpdateTime(c.getManufactureId());
						addMftUnreadInfo(c.getOrderNo(), currUserId1, c.getManufactureId());
					}
				} catch (Exception e) {
					log.error("编辑包装工艺指导书外壳变更记录错误", e);
				}
			}});
	}

	@Override
	public void editFittingRecord(ManufactureFitting mftFittingBefore,
			ManufactureFittingCommand c, Integer currUserId1, String currUserName) {
		if (mftFittingBefore == null) return;
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					String currUserId = currUserId1 +"";
					AtomicInteger i = new AtomicInteger(0);// 统计变成项的多少，更新订单为未读
					ManufactureAlt alt1 = new ManufactureAlt(new Manufacture(c.getManufactureId()), currUserName);
					ManufactureFitting h = mftFittingBefore;
					Class<? extends ManufactureFittingCommand> cl = c.getClass();
					Method[] methods = cl.getMethods();
					for (Method m : methods) {
						for (Annotation a : m.getAnnotations()) {
							if (a instanceof Description) {
								if ("图片".equals(((Description) a).value())) {
									String before = "";
									if (h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]) != null) {
										before = (h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0])+"").substring(c.getPictPaths().lastIndexOf("/")+1);
									}
									// System.err.println("丝印2::::"+m.invoke(c,new Object[0]));
									String after = "";
									if (m.invoke(c, new Object[0]) != null) {
										after = (m.invoke(c, new Object[0])+"").substring(c.getPictPaths().lastIndexOf("/")+1);
									}
									compareString(before, after, "配件-"+c.getName()+"-"+((Description) a).value(), alt1, currUserId, c.getOrderNo(), i);
								} else {
									compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), "配件-"+c.getName()+"-"+((Description) a).value(), alt1, currUserId, c.getOrderNo(), i);
								}
							}
						}
					}
					if (i.get() > 0) {
						//更新未读及时间戳
						manufactureServiceI.updateUpdateTime(c.getManufactureId());
						addMftUnreadInfo(c.getOrderNo(), currUserId1, c.getManufactureId());
					}
				} catch (Exception e) {
					log.error("编辑包装工艺指导书配件变更记录错误", e);
				}
			}
		});
	}

	@Override
	public void editPackageRecord(ManufacturePackage mftPackageBefore,
			ManufacturePackageCommand c, Integer currUserId1, String currUserName) {
		if (mftPackageBefore == null) return;
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					String currUserId = currUserId1 +"";
					AtomicInteger i = new AtomicInteger(0);// 统计变成项的多少，更新订单为未读
					ManufactureAlt alt1 = new ManufactureAlt(new Manufacture(c.getManufactureId()), currUserName);
					ManufacturePackage h = mftPackageBefore;
					Class<? extends ManufacturePackageCommand> cl = c.getClass();
					Method[] methods = cl.getMethods();
					for (Method m : methods) {
						for (Annotation a : m.getAnnotations()) {
							if (a instanceof Description) {
								String item = c.getPictTitle()==null?"":"-"+c.getPictTitle();
								if ("图片".equals(((Description) a).value())) {
									String before = "";
									if (h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]) != null) {
										before = (h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0])+"").substring(c.getPictPaths().lastIndexOf("/")+1);
									}
									String after = "";
									if (m.invoke(c, new Object[0]) != null) {
										after = (m.invoke(c, new Object[0])+"").substring(c.getPictPaths().lastIndexOf("/")+1);
									}
									compareString(before, after, "包材及包装-"+c.getTitleName()+item+"-"+((Description) a).value(), alt1, currUserId, c.getOrderNo(), i);
								} else {
									compareString(h.getClass().getMethod(m.getName(), new Class[0]).invoke(h, new Object[0]), m.invoke(c, new Object[0]), "包材及包装-"+c.getTitleName()+item+"-"+((Description) a).value(), alt1, currUserId, c.getOrderNo(), i);
								}
							}
						}
					}
					if (i.get() > 0) {
						//更新未读及时间戳
						manufactureServiceI.updateUpdateTime(c.getManufactureId());
						addMftUnreadInfo(c.getOrderNo(), currUserId1, c.getManufactureId());
					}
				} catch (Exception e) {
					log.error("编辑包装工艺指导书包材变更记录错误", e);
				}
			}
		});
	}

	@Override
	public void addShellRecord(ManufactureShellCommand c,
			Integer currUserId1, String currUserName) {
		if (c == null) return;
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					String currUserId = currUserId1 +"";
					AtomicInteger i = new AtomicInteger(0);// 统计变成项的多少，更新订单为未读
					ManufactureAlt alt1 = new ManufactureAlt(new Manufacture(c.getManufactureId()), currUserName);
					String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
					compareString("", "名称："+c.getName()+"；图片："+path+"；颜色："+c.getColor()+"；喷漆工艺："+c.getCraft()+ "；丝印："+c.getSilk()+ "；备注："+c.getDescription(), "外壳", alt1, currUserId, c.getOrderNo(), i);
					manufactureServiceI.updateUpdateTime(c.getManufactureId());
					addMftUnreadInfo(c.getOrderNo(), currUserId1, c.getManufactureId());
				} catch (Exception e) {
					log.error("增加包装工艺指导书外壳变更记录错误", e);
				}
			}
		});
	}

	@Override
	public void addFittingRecord(ManufactureFittingCommand c,
			Integer currUserId1, String currUserName) {
		if (c == null) return;
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					String currUserId = currUserId1 +"";
					AtomicInteger i = new AtomicInteger(0);// 统计变成项的多少，更新订单为未读
					ManufactureAlt alt1 = new ManufactureAlt(new Manufacture(c.getManufactureId()), currUserName);
					String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
					compareString("", "名称："+c.getName()+"；图片："+path+"；型号/规格："+c.getSpecification()+"；包装袋："+c.getPackBag()+ "；标贴："+c.getLabel()+ "；备注："+c.getDescription(), "配件", alt1, currUserId, c.getOrderNo(), i);
					manufactureServiceI.updateUpdateTime(c.getManufactureId());
					addMftUnreadInfo(c.getOrderNo(), currUserId1, c.getManufactureId());
				} catch (Exception e) {
					log.error("增加包装工艺指导书配件变更记录错误", e);
				}
			}
		});
	}

	@Override
	public void addPackageRecord(ManufacturePackageCommand c,
			Integer currUserId1, String currUserName) {
		if (c == null) return;
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					String currUserId = currUserId1 +"";
					AtomicInteger i = new AtomicInteger(0);// 统计变成项的多少，更新订单为未读
					ManufactureAlt alt1 = new ManufactureAlt(new Manufacture(c.getManufactureId()), currUserName);
					String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
					compareString("", "标题："+c.getPictTitle()+"；图片："+path+"；描述："+c.getDescription(), "包材及包装-"+c.getTitleName(), alt1, currUserId, c.getOrderNo(), i);
					manufactureServiceI.updateUpdateTime(c.getManufactureId());
					addMftUnreadInfo(c.getOrderNo(), currUserId1, c.getManufactureId());
				} catch (Exception e) {
					log.error("增加包装工艺指导书包材变更记录错误", e);
				}
			}
		});		
	}

	@Override
	public void delShellRecord(ManufactureShell c,
			Integer currUserId1, String currUserName) {
		if (c == null) return;
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					String orderNo = manufactureServiceI.get(c.getMftid()).getOrderNo();
					String currUserId = currUserId1 +"";
					AtomicInteger i = new AtomicInteger(0);// 统计变成项的多少，更新订单为未读
					ManufactureAlt alt1 = new ManufactureAlt(new Manufacture(c.getMftid()), currUserName);
					String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
					compareString("名称："+c.getName()+"；图片："+path+"；颜色："+c.getColor()+"；喷漆工艺："+c.getCraft()+ "；丝印："+c.getSilk()+ "；备注："+c.getDescription(), "", "外壳", alt1, currUserId, orderNo, i);
					manufactureServiceI.updateUpdateTime(c.getMftid());
					addMftUnreadInfo(orderNo, currUserId1, c.getMftid());
				} catch (Exception e) {
					log.error("删除包装工艺指导书外壳变更记录错误", e);
				}
			}
		});	
	}

	@Override
	public void delFittingRecord(ManufactureFitting c,
			Integer currUserId1, String currUserName) {
		if (c == null) return;
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					String orderNo = manufactureServiceI.get(c.getMftid()).getOrderNo();
					String currUserId = currUserId1 +"";
					AtomicInteger i = new AtomicInteger(0);// 统计变成项的多少，更新订单为未读
					ManufactureAlt alt1 = new ManufactureAlt(new Manufacture(c.getMftid()), currUserName);
					String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
					compareString("名称："+c.getName()+"；图片："+path+"；型号/规格："+c.getSpecification()+"；包装袋："+c.getPackBag()+ "；标贴："+c.getLabel()+ "；备注："+c.getDescription(), "", "配件", alt1, currUserId, orderNo, i);
					manufactureServiceI.updateUpdateTime(c.getMftid());
					addMftUnreadInfo(orderNo, currUserId1, c.getMftid());
				} catch (Exception e) {
					log.error("删除包装工艺指导书配件变更记录错误", e);
				}
			}
		});	
	}

	@Override
	public void delPackageRecord(ManufacturePackage c,
			Integer currUserId1, String currUserName) {
		if (c == null) return;
		ThreadPoolBiz.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					String orderNo = manufactureServiceI.get(c.getMftid()).getOrderNo();
					String currUserId = currUserId1 +"";
					AtomicInteger i = new AtomicInteger(0);// 统计变成项的多少，更新订单为未读
					ManufactureAlt alt1 = new ManufactureAlt(new Manufacture(c.getMftid()), currUserName);
					String path = ""; if (c.getPictPaths() != null)  path = c.getPictPaths().substring(c.getPictPaths().lastIndexOf("/")+1);
					compareString("标题："+c.getPictTitle()+"；图片："+path+"；描述："+c.getDescription(), "", "包材及包装-"+manufacturePackageTitleService.get(c.getTitleId()).getName(), alt1, currUserId, orderNo, i);
					manufactureServiceI.updateUpdateTime(c.getMftid());
					addMftUnreadInfo(orderNo, currUserId1, c.getMftid());
				} catch (Exception e) {
					log.error("删除包装工艺指导书配件变更记录错误", e);
				}
			}
		});	
	}
}
