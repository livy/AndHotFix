package cn.jiajixin.nuwa;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import cn.jiajixin.nuwa.util.AssetUtils;
import cn.jiajixin.nuwa.util.DexUtils;
import cn.jiajixin.nuwa.util.SecurityChecker;

/**
 * Created by jixin.jia on 15/10/31.
 */
public class Nuwa {

    private static final String TAG = "nuwa";
    private static final String HACK_DEX = "hack.apk";

    private static final String DEX_DIR = "nuwa";
    private static final String DEX_OPT_DIR = "nuwaopt";

    /**
     * security check
     */
    private static SecurityChecker mSecurityChecker;
    private static File mDexDir;

    public static void initial(Context context) throws NuwaException {
        mSecurityChecker = new SecurityChecker(context.getApplicationContext());
        mDexDir = new File(context.getFilesDir(), DEX_DIR);
        mDexDir.mkdir();
        String dexPath = null;
        try {
            dexPath = AssetUtils.copyAsset(context, HACK_DEX, mDexDir);
        } catch (IOException e) {
            Log.e(TAG, "copy " + HACK_DEX + " failed");
            throw new NuwaException(e.getMessage());
        }

        loadPatch(context, dexPath);
    }


    public static void loadPatch(Context context, String dexPath) throws NuwaException {
        Log.d(TAG, "loadPatch:" + dexPath);
        if (context == null || TextUtils.isEmpty(dexPath)) {
            Log.e(TAG, "context is null");
            throw new NuwaException("context is null");
        }
        File dexFile = new File(dexPath);
        if (!dexFile.exists()) {
            Log.e(TAG, dexPath + " is null");
            throw new NuwaException(dexPath + " is null");
        }
        if (!mSecurityChecker.verifyApk(dexFile)) {
            Log.e(TAG, dexPath + "verifyApk failed");
            throw new NuwaException("verifyApk failed");
        }

        File dexOptDir = new File(context.getFilesDir(), DEX_OPT_DIR);
        dexOptDir.mkdir();
        try {
            DexUtils.injectDexAtFirst(dexPath, dexOptDir.getAbsolutePath());
            Log.d(TAG, "loadPatch success:" + dexPath);
        } catch (Exception e) {
            Log.e(TAG, "inject " + dexPath + " failed");
            throw new NuwaException(e.getMessage());
        }
    }
}
