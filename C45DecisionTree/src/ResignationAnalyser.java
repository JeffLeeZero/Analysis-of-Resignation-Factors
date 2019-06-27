import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ResignationAnalyser {

    void trainModel();

    String doPrediction(List<String> data);

    double getAccuracy();


    Map<String,String> improveMeasure();

    double getProbability();

    Map<String,Double> getAttrRatio();

    Map<String,Double> getAttrRatio(String attrName);


}
