package me.zixing.body37.main;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.callStaticMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Context;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
public class HelperInit implements IXposedHookLoadPackage {

	private static final String PACKAGE_NAME = "com.body37.light";
	@Override
	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
		//判断包名，确定hook应用
		if(!PACKAGE_NAME.contains(lpparam.packageName)){
			return;
		}else{
			//获取Context对象
			Object activityThread = callStaticMethod(findClass("android.app.ActivityThread", null), "currentActivityThread");
			Context mContext = (Context)callMethod(activityThread, "getSystemContext");
			//打印xposed log
			XposedBridge.log(lpparam.packageName+":"+lpparam.appInfo.targetSdkVersion);
			//测试hookMethod
			findAndHookConstructor("com.body37.light.activity.set.SocialNetworkActivity", lpparam.classLoader, new XC_MethodHook() {

				@Override
				protected void beforeHookedMethod(MethodHookParam param)
						throws Throwable {
					XposedBridge.log("beforeHookedMethod");
				}

				@Override
				protected void afterHookedMethod(MethodHookParam param)
						throws Throwable {
					XposedBridge.log("afterHookedMethod");
				}
				
			});
			
			findAndHookMethod("body37light.qa.a", lpparam.classLoader, "a", findClass("body37light.qg.a", lpparam.classLoader),new XC_MethodHook(){

				@Override
				protected void beforeHookedMethod(MethodHookParam param)
						throws Throwable {
					Object o = param.args[0];
					Field[] fields = o.getClass().getFields();
					for (Field field : fields) {
						XposedBridge.log(field.getName()+":"+field.get(o));
					}
				}
				
			});
			
			
			/**
			 * 修改qq运动步数
			 */
			findAndHookConstructor("body37light.qa.a", lpparam.classLoader, android.content.Context.class,java.lang.String.class,java.util.ArrayList.class,new XC_MethodHook(){

				@Override
				protected void beforeHookedMethod(MethodHookParam param)
						throws Throwable {
					ArrayList list = (ArrayList) param.args[2];
					for (Object object : list) {
						Class<?> class1 = findClass("body37light.mx", lpparam.classLoader);
						Field[] fields = class1.getFields();
						for (Field field : fields) {
							if("c".equals(field.getName())){
								field.set(object, 100000);
							}
						}
					}
				}
				
			});
			
			
			/**
			 * 修改微信步数
			 */
			findAndHookMethod("body37light.qh", lpparam.classLoader, "b" ,android.content.Context.class ,new XC_MethodHook(){

				@Override
				protected void afterHookedMethod(MethodHookParam param)
						throws Throwable {
					Object result = param.getResult();
					Class<?> class1 = findClass("body37light.nb", lpparam.classLoader);
					Field[] fields = class1.getFields();
					for (Field field : fields) {
						if("g".equals(field.getName())){
							field.set(result, 100000);
						}else{
							field.set(result, 0);
						}
						XposedBridge.log(field.getName()+":"+field.get(result));
					}
				}
				
			});
			
			
			findAndHookMethod("com.body37.light.activity.set.SocialNetworkActivity.9", lpparam.classLoader, "g" , new XC_MethodHook(){

				@Override
				protected void beforeHookedMethod(MethodHookParam param)
						throws Throwable {
					XposedBridge.log("before nikiname");
					
				}

				@Override
				protected void afterHookedMethod(MethodHookParam param)
						throws Throwable {
					XposedBridge.log("after nikiname");
					param.setResult("李想");
				}
				
			});
			
		}
	}

}
