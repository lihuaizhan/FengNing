package com.example.tps900.tps900.Utlis;

import com.example.tps900.tps900.Bean.InfoSetting;

import java.io.InputStream;
import java.util.List;

/**
 *信息设置xml解析接口
 * Created by Administrator on 2017/7/10 0010.
 */

public interface InfoSettingParser {
    /**
     *
     * 解析输入流，获取InFoSetting列表
     * @param is
     * @return
     * @throws Exception
     */
    public List<InfoSetting> parse(InputStream is) throws Exception;

    /**
     *
     * 序列化InFoSetting对象集合，得到XML形式的字符串
     * @param beauties
     * @return
     * @throws Exception
     */
    public String serialize(List<InfoSetting> beauties) throws Exception;
}
