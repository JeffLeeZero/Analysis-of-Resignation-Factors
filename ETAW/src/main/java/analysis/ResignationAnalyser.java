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
    ArrayList<String> getProbability(ArrayList<String> data, String aid, String department);

    /**
     * 获取每个因变量的比重
     *
     * @return arrayList<String>类型 其中每一个元素包含三个数值，以“，”分割，之后进行数据处理可以采用StringD的split方法
     * 每一个String元素中，第一个值代表是否会离职，[0]留任或者[1]离职；第二个值是预测可能性，其中包含两个数值，相加为1
     * 第三个值是该模型的拟合程度
     */
    ArrayList<Float> getResult(ArrayList<String> result, int index);
    /**
     * 获取py脚本返回的分析数据
     *
     * @return index代表需要的数据，0代表是否离职，1和2分别是离职和留任的可能性（可能是），3代表模型拟合程度
     *
     */
    Map<String,Double> getAttrRatio();

    /**
     * 获取该变量每个取值对应的离职率
     * @param attrName
     * @return
     */
    Map<String,Double> getAttrRatio(String attrName);


}
