package pw.rabit.xposed.xdebuggable;

import android.content.pm.ApplicationInfo;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class GlobalPMSHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (loadPackageParam.packageName.equals("android")) {
            XposedHelpers.findAndHookMethod("com.android.server.pm.PackageManagerService", loadPackageParam.classLoader,
                    "getApplicationInfo", String.class, int.class, int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            ApplicationInfo ai = (ApplicationInfo) param.getResult();
                            if (ai != null) {
                                // Make the app debuggable
                                ai.flags |= ApplicationInfo.FLAG_DEBUGGABLE;
                            }
                        }
                    }
            );
        }
    }
}
