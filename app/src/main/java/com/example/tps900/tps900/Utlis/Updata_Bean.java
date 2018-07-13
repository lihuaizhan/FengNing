package com.example.tps900.tps900.Utlis;

import java.util.List;

/**
 * 项目名称：CQPda550_NEW
 * 类名称：Updata_Bean
 * 类描述：获取json返回来的数据用于解析实体类
 * 创建人：zxh
 * 创建时间：2017/3/21 9:28
 * 修改人：zxh
 * 修改时间：2017/3/21 9:28
 * 修改备注：
 */

public class Updata_Bean {


    public List<SuccessBean> success;

    public List<SuccessBean> getSuccess() {
        return success;
    }

    public void setSuccess(List<SuccessBean> success) {
        this.success = success;
    }

    public static class SuccessBean {

        public String Names;
        public String EndData;
        public String DownLoadURL;
        public String VersionCode;
        public String VersionName;
        public String BeginData;

        public String getNames() {
            return Names;
        }

        public void setNames(String names) {
            Names = names;
        }

        public String getEndData() {
            return EndData;
        }

        public void setEndData(String endData) {
            EndData = endData;
        }

        public String getDownLoadURL() {
            return DownLoadURL;
        }

        public void setDownLoadURL(String downLoadURL) {
            DownLoadURL = downLoadURL;
        }

        public String getVersionCode() {
            return VersionCode;
        }

        public void setVersionCode(String versionCode) {
            VersionCode = versionCode;
        }

        public String getVersionName() {
            return VersionName;
        }

        public void setVersionName(String versionName) {
            VersionName = versionName;
        }

        public String getBeginData() {
            return BeginData;
        }

        public void setBeginData(String beginData) {
            BeginData = beginData;
        }
    }
}
