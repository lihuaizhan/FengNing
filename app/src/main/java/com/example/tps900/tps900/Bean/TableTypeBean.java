package com.example.tps900.tps900.Bean;

import java.util.ArrayList;

/**
 * 项目名称：PDAXianShang
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2018-03-11 13:09
 * 修改人：zxh
 * 修改时间：2018-03-11 13:09
 * 修改备注：
 */

public class TableTypeBean {

    /**
     * Flags : true
     * ls : [{"TypeID":"2","TypeName":"包间桌"},{"TypeID":"2","TypeName":"包间桌"}]
     */

    public boolean Flags;
    public ArrayList<LsBean> ls;

    public boolean isFlags() {
        return Flags;
    }

    public void setFlags(boolean flags) {
        Flags = flags;
    }

    public ArrayList<LsBean> getLs() {
        return ls;
    }

    public void setLs(ArrayList<LsBean> ls) {
        this.ls = ls;
    }

    public static class LsBean {
        /**
         * TypeID : 2
         * TypeName : 包间桌
         */

        public String TypeID;
        public String TypeName;

        public String getTypeID() {
            return TypeID;
        }

        public void setTypeID(String typeID) {
            TypeID = typeID;
        }

        public String getTypeName() {
            return TypeName;
        }

        public void setTypeName(String typeName) {
            TypeName = typeName;
        }
    }
}
