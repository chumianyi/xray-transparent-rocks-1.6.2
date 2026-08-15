package com.xray;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.Name("XrayCore")
@IFMLLoadingPlugin.MCVersion("1.6.2")
@IFMLLoadingPlugin.TransformerExclusions({"com.xray."})
public class XrayCorePlugin implements IFMLLoadingPlugin {

    static {
        System.out.println("[XrayMod] XrayCorePlugin loaded - coremod initializing");
    }

    @Override
    public String[] getASMTransformerClass() {
        System.out.println("[XrayMod] Registering ASM transformer: com.xray.XrayClassTransformer");
        return new String[]{"com.xray.XrayClassTransformer"};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        System.out.println("[XrayMod] XrayCorePlugin.injectData called with " + data.size() + " entries");
    }

    @Override
    public String[] getLibraryRequestClass() {
        return null;
    }
}
