package org.evgen.view.windows;

public class WindowHandler {
    private final ContouringSettings contouringSettings = new ContouringSettings();
    private final DitheringSettingsFrame fsDitheringSettings = new DitheringSettingsFrame();
    private final DitheringSettingsFrame oDitheringSettings = new DitheringSettingsFrame();
    private final GammaSettingsFrame gammaSettingsFrame = new GammaSettingsFrame();
    private final GaussianSettingsFrame gaussianSettingsFrame = new GaussianSettingsFrame();
    private final RotateSettingsWindow rotateSettingsWindow = new RotateSettingsWindow();

    public ContouringSettings getContouringSettings() {
        return contouringSettings;
    }

    public DitheringSettingsFrame getFsDitheringSettings() {
        return fsDitheringSettings;
    }

    public DitheringSettingsFrame getoDitheringSettings() {
        return oDitheringSettings;
    }

    public GammaSettingsFrame getGammaSettingsFrame() {
        return gammaSettingsFrame;
    }

    public GaussianSettingsFrame getGaussianSettingsFrame() {
        return gaussianSettingsFrame;
    }

    public RotateSettingsWindow getRotateSettingsWindow() {
        return rotateSettingsWindow;
    }
}
