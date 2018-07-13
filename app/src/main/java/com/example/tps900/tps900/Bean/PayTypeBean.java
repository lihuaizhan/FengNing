package com.example.tps900.tps900.Bean;

/**
 * 项目名称：PDA_XianShang
 * 类名称：
 * 类描述：
 * 创建人：zxh
 * 创建时间：2017/10/9 10:54
 * 修改人：zxh
 * 修改时间：2017/10/9 10:54
 * 修改备注：
 */

public class PayTypeBean {
    /**
     * TypeCode : 005
     * TypeName : 一卡通
     */

    public String TypeCode;
    public String TypeName;
    public boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTypeCode() {
        return TypeCode;
    }

    public void setTypeCode(String typeCode) {
        TypeCode = typeCode;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }
}
