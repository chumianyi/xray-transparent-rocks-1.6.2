package com.xray;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.Name("XrayCore")
@IFMLLoadingPlugin.MCVersion("1.6.2")
@IFMLLoadingPlugin.TransformerExclusions({"com.xray."})
public class XrayCorePlugin implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
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
    }

    @Override
    public String[] getLibraryRequestClass() {
        return null;
    }
}
