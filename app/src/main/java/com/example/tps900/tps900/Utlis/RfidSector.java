package com.example.tps900.tps900.Utlis;

/**
 * Created by liangmj170807 on 2017/9/14.
 */

public class RfidSector {


    public static final int AUTH_KEY_A = 0x0A;
    public static final int AUTH_KEY_B = 0X0B;

    private int mAuthKeyType = AUTH_KEY_A;
    private byte[] mAuthKey = {(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff};

    private int mIndex = 0;
    private Block mOperateBlock;

    public RfidSector() {
    }

    public RfidSector(int index, byte[] authKey, int keyType) {
        mIndex = index;
        mAuthKeyType = keyType;
        mAuthKey = authKey;
    }

    public int getAuthKeyType() {
        return mAuthKeyType;
    }

    public byte[] getAuthKey() {
        return mAuthKey;
    }

    public void setKey(byte[] key, int type) {
        mAuthKey = key;
        mAuthKeyType = type;
    }

    public Block getOperateBlock() {
        return mOperateBlock;
    }

    public void setOperateBlock(Block operateBlock) {
        mOperateBlock = operateBlock;
    }

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    public static class Block {
        public int index = 0;
        public byte[] data = null;

        public Block(int _index) {
            index = _index;
        }
    }
}
