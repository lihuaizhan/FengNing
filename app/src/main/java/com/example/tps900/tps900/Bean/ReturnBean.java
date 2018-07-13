package com.example.tps900.tps900.Bean;

/**
 * Created by wo on 2017/5/16.
 */

public class ReturnBean {


    /**
     * NCOMMID : 13
     * NPCOMMID : -1
     * PNUMBER : 1.0
     * NNUMBER : 1.0
     * NPRICE : 2.156
     * RNUMBER : 0.0
     * RCANNUMBER : 1.0
     * VCOMMYNAME : 俄方
     */

    private int NCOMMID;
    private int NPCOMMID;
    private double NPNUMBER;
    private int NNUMBER;
    private double NPRICE;
    private double RNUMBER;
    private int RCANNUMBER;
    private String VCOMMYNAME;
    private int RETURNNUMBER;
    private boolean flag;

    public int getNCOMMID() {
        return NCOMMID;
    }

    public void setNCOMMID(int NCOMMID) {
        this.NCOMMID = NCOMMID;
    }

    public int getNPCOMMID() {
        return NPCOMMID;
    }

    public void setNPCOMMID(int NPCOMMID) {
        this.NPCOMMID = NPCOMMID;
    }

    public double getPNUMBER() {
        return NPNUMBER;
    }

    public void setPNUMBER(double PNUMBER) {
        this.NPNUMBER = PNUMBER;
    }

    public int getNNUMBER() {
        return NNUMBER;
    }

    public void setNNUMBER(int NNUMBER) {
        this.NNUMBER = NNUMBER;
    }

    public double getNPRICE() {
        return NPRICE;
    }

    public void setNPRICE(double NPRICE) {
        this.NPRICE = NPRICE;
    }

    public double getRNUMBER() {
        return RNUMBER;
    }

    public void setRNUMBER(double RNUMBER) {
        this.RNUMBER = RNUMBER;
    }

    public int getRCANNUMBER() {
        return RCANNUMBER;
    }

    public void setRCANNUMBER(int RCANNUMBER) {
        this.RCANNUMBER = RCANNUMBER;
    }

    public String getVCOMMYNAME() {
        return VCOMMYNAME;
    }

    public void setVCOMMYNAME(String VCOMMYNAME) {
        this.VCOMMYNAME = VCOMMYNAME;
    }

    public void setRETURNNUMBER(int RETURNNUMBER) {
        this.RETURNNUMBER = RETURNNUMBER;
    }

    public int getRETURNNUMBER() {
        return RETURNNUMBER;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }
}
