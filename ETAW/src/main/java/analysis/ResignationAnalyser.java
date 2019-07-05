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
     * 获取模型准确率
     * @return
     */
    double getAccuracy();

    /**
     * 获取改进（挽留）员工措施
     * @return [0]关键因素名称，[1,2,3...]如何改变可以挽回
     */
    List<String> improveMeasure();

    /**
     * 获取每个因变量的比重
     *
     * @return arrayList<String>类型 其中每一个元素包含三个数值，以“，”分割，之后进行数据处理可以采用StringD的split方法
     * 每一个String元素中，第一个值代表是否会离职，[0]留任或者[1]离职；第二个值是预测可能性，其中包含两个数值，相加为1
     * 第三个值是该模型的准确率
     */
    ArrayList<String> getProbability(ArrayList<String> data);



    /**
     * 获取每个因素对结果的影响性（信息增益率）
     * @return
     */
    Map<String,Double> getAttrRatio();

    /**
     * 获取该变量每个取值对应的离职率
     * @param attrName
     * @return
     */
    Map<String,Double> getAttrRatio(String attrName);


    /**
     * 获取批量导入的测试数据集结果
     * @return
     * [[判断结果，准确率],[判断结果，准确率],……]，
     * 判断结果取值范围{"离职","不离职"}
     */
    ArrayList<ArrayList<String>> getProbabilityFromCSV(String csvURL);


}
