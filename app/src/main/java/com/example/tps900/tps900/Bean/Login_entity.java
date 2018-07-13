package com.example.tps900.tps900.Bean;

import java.util.List;
/**
 * 项目名称：TPS900
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/4/18 14:48
 * 修改人：zxh
 * 修改时间：2017/4/18 14:48
 * 修改备注：
 */

public class Login_entity {

    /**
     * devices : [{"NDEVICEID":3091,"SDEVICENAME":"PDA01","SDEVICEIP":"192.168.0.114","NPARKID":2555,"SPARKNAME":"欢乐时光"}]
     * flag : true
     * erro :
     */

    public boolean FLAG;
    public String ERRO;
    public List<DevicesBean> devices;

    public boolean isFlag() {
        return FLAG;
    }

    public void setFlag(boolean flag) {
        this.FLAG = flag;
    }

    public String getErro() {
        return ERRO;
    }

    public void setErro(String erro) {
        this.ERRO = erro;
    }

    public List<DevicesBean> getDevices() {
        return devices;
    }

    public void setDevices(List<DevicesBean> devices) {
        this.devices = devices;
    }

    public static class DevicesBean {
        /**
         * NDEVICEID : 3091
         * SDEVICENAME : PDA01
         * SDEVICEIP : 192.168.0.114
         * NPARKID : 2555
         * SPARKNAME : 欢乐时光
         */

        public int NDEVICEID;
        public String SDEVICENAME;
        public String SDEVICEIP;
        public int NPARKID;
        public String SPARKNAME;

        public int getNDEVICEID() {
            return NDEVICEID;
        }

        public void setNDEVICEID(int NDEVICEID) {
            this.NDEVICEID = NDEVICEID;
        }

        public String getSDEVICENAME() {
            return SDEVICENAME;
        }

        public void setSDEVICENAME(String SDEVICENAME) {
            this.SDEVICENAME = SDEVICENAME;
        }

        public String getSDEVICEIP() {
            return SDEVICEIP;
        }

        public void setSDEVICEIP(String SDEVICEIP) {
            this.SDEVICEIP = SDEVICEIP;
        }

        public int getNPARKID() {
            return NPARKID;
        }

        public void setNPARKID(int NPARKID) {
            this.NPARKID = NPARKID;
        }

        public String getSPARKNAME() {
            return SPARKNAME;
        }

        public void setSPARKNAME(String SPARKNAME) {
            this.SPARKNAME = SPARKNAME;
        }
    }
}
