package com.apical.oddm.core.model.order;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.springframework.context.annotation.Description;

public class Test {

	public static void main(String[] args) throws Exception {
		OrderOS cmd = new OrderOS();
		cmd.setBootLogo("boot");
		Class<? extends OrderOS> cl = cmd.getClass();
		Method[] methods = cl.getMethods();
		for (Method m : methods) {
			if (m.getName().startsWith("get") && !"getClass".equals(m.getName())) {
				for (Annotation a :m.getAnnotations()) {
					System.out.println((a instanceof Description));
					Description d = (Description) a;
					System.out.println(d.value());
				}
				System.out.println(m.invoke(cmd, null));
			}
				
		}
		Method method = cmd.getClass().getMethod("getBootLogo", null);
        System.out.println(method.getName() + "():  Get Value is   " + method.invoke(cmd, null));
	}
	/*compareString(orderOS.getBootLogo(), orderOSCommand.getBootLogo(), "开机画面", alt);
	compareString(orderOS.getGui(), orderOSCommand.getGui(), "GUI", alt);
	compareString(orderOS.getIscustom()+"", orderOSCommand.getIscustom()+"", "软件信息", alt);
	compareString(orderOS.getLangDefault(), orderOSCommand.getLangDefault(), "默认语言", alt);
	compareString(orderOS.getLangOs(), orderOSCommand.getLangOs(), "系统语言", alt);
	compareString(orderOS.getPreFile(), orderOSCommand.getPreFile(), "预存文件", alt);
	compareString(orderOS.getPreMap(), orderOSCommand.getPreMap(), "预存地图", alt);
	compareString(orderOS.getTimezone(), orderOSCommand.getTimezone(), "地区时间", alt);
	compareString(orderOS.getUuid(), orderOSCommand.getUuid(), "UUID号", alt);*/
}
