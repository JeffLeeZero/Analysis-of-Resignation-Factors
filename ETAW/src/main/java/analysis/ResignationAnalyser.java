package analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ResignationAnalyser {

    /**
     * 训练模型并保存
     */
    void trainModel(String url);

    /**
     * 根据输入获取预测结果
     * @param data
     * @return
     * 未完成
     */
    String doPrediction(ArrayList<String> data);

    /**
     * 获取模型准确率
     * @return
     */
    double getAccuracy();

    /**
     * 获取改进（挽留）员工措施
     * @return
     * 未完成
     */
    Map<String,String> improveMeasure();

    /**
     * 获取员工离职可能性
     * @return
     * 未完成
     */
    double getProbability(ArrayList<String> data);

    /**
     * 获取每个因变量的比重
     * @return
     */
    Map<String,Double> getAttrRatio();

    /**
     * 获取该变量每个取值对应的离职率
     * @param attrName
     * @return
     */
    Map<String,Double> getAttrRatio(String attrName);


}
