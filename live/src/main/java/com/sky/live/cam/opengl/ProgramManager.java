package com.sky.live.cam.opengl;

public class ProgramManager {


    public ProgramManager() {
    }

    private ProgramTexture2d mProgramTexture2D;
    private ProgramTextureOES mProgramTextureOES;

    public Program getProgram(Constants.TextureFormat srcTetxureFormat){
        switch (srcTetxureFormat){
            case Texure2D:
                if (null == mProgramTexture2D){
                    mProgramTexture2D = new ProgramTexture2d();
                }
                return mProgramTexture2D;
            case Texture_Oes:
                if (null == mProgramTextureOES) {
                    mProgramTextureOES = new ProgramTextureOES();
                }
                return mProgramTextureOES;
        }
        return null;

    }

    public void release(){
        if (null != mProgramTexture2D){
            mProgramTexture2D.release();
            mProgramTexture2D = null;

        }
        if (null != mProgramTextureOES){
            mProgramTextureOES.release();
            mProgramTextureOES = null;

        }
    }
}
