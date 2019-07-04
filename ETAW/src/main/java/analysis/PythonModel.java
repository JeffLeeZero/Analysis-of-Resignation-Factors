package analysis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PythonModel {
    private String aid;

    public PythonModel(String aid){
        this.aid = aid;
    }

    public void trainModel(String url){
        try{
            Process svmProc,logProc;
            String[] svmProcData = new String[]{"python"
                    , "C:\\Users\\west\\Desktop\\Analysis-of-Resignation-Factors\\ETAW\\src\\main\\java\\logisticregression\\svm_train.py"
                    , aid, url};
            svmProc = Runtime.getRuntime().exec(svmProcData);
            String[] logProcData = new String[]{"python"
                    , "C:\\Users\\west\\Desktop\\Analysis-of-Resignation-Factors\\ETAW\\src\\main\\java\\logisticregression\\log_reg_train.py"
                    , aid, url};
            logProc = Runtime.getRuntime().exec(logProcData);
            svmProc.waitFor();
            logProc.waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Double>> getProbability(ArrayList<String> data, String department) {
        Process proc;
        ArrayList<String> dataset = new ArrayList<>();
        try{
            String testDatas = String.join(",", data);
            //String[] fileData = new String[]{"python", "src\\main\\java\\logisticregression\\analyze.py",testDatas,choosemodel,department};
            String[] fileData = new String[]{"python", "C:\\Users\\west\\Desktop\\Analysis-of-Resignation-Factors\\ETAW\\src\\main\\java\\logisticregression\\analyze.py", testDatas,aid,department};
            proc = Runtime.getRuntime().exec(fileData);
            BufferedReader in =  new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = in.readLine()) != null){
                dataset.add(line);
            }
            in.close();
            proc.waitFor();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        result.add(new ArrayList<Double>());
        result.add(new ArrayList<Double>());
        int count = 3;
        result.get(0).add(Double.valueOf(dataset.get(0)));
        result.get(0).add(Double.valueOf(dataset.get(2)));
        result.get(1).add(Double.valueOf(dataset.get(count)));
        result.get(1).add(Double.valueOf(dataset.get(count+2)));
        return result;
    }

    public ArrayList<ArrayList<ArrayList<Double>>> getProbabilityFromCSV(String csvURL){
        Process proc;
        ArrayList<String> dataset = new ArrayList<>();
        try{
            String[] fileData = new String[]{"python", "C:\\Users\\west\\Desktop\\Analysis-of-Resignation-Factors\\ETAW\\src\\main\\java\\logisticregression\\analyze_csv.py", csvURL,aid};
            //String[] fileData = new String[]{"python", "src\\main\\java\\logisticregression\\analyze_csv.py", csvURL,aid};
            proc = Runtime.getRuntime().exec(fileData);
            BufferedReader in =  new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = in.readLine()) != null){
                dataset.add(line);
            }
            in.close();
            proc.waitFor();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        /*
        [
            [(逻辑回归模型)：[判断结果，准确率]，
             （SVM）：[判断结果，准确率]，
              （决策树）：[判断结果，准确率]，
              （随机森林）：[判断结果，准确率]
            ]，
            [……],
            ……
        ]
         */
        ArrayList<ArrayList<ArrayList<Double>>> results = new ArrayList<>();

        //预测员工的人数
        int count = dataset.size()/4;
        //每个模型的数据长度
        int length = dataset.size()/2;
        ArrayList<ArrayList<Double>> result;
        ArrayList<Double> model1;
        ArrayList<Double> model2;
        for(int i = 0;i < count;i++){
            result = new ArrayList<>();
            model1 = new ArrayList<>();
            model2 = new ArrayList<>();
            model1.add(Double.valueOf(dataset.get(i*2)));
            model1.add(Double.valueOf(dataset.get(i*2+1)));
            model2.add(Double.valueOf(dataset.get(i*2+length)));
            model2.add(Double.valueOf(dataset.get(i*2+length+1)));
            result.add(model1);
            result.add(model2);
            results.add(result);
        }
        return results;
    }
}
