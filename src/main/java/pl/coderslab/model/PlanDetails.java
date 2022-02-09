package pl.coderslab.model;

import java.util.Date;
import java.util.List;

public class PlanDetails extends Plan{

    private List<PlanDetailClass> listPlanDetailClass;

    public PlanDetails(){}

    public PlanDetails(String name, String description, Date created, Admin admin, List<PlanDetailClass> listPlanDetailClass){
        super(name, description, created, admin);
        this.listPlanDetailClass = listPlanDetailClass;
    }

    public PlanDetails(String name, String description, Admin admin, List<PlanDetailClass> listPlanDetailClass){
        super(name, description, admin);
        this.listPlanDetailClass = listPlanDetailClass;
    }

    public List<PlanDetailClass> getListPlanDetailClass() {
        return listPlanDetailClass;
    }

    public void setListPlanDetails(List<PlanDetailClass> listPlanDetailClass) {
        this.listPlanDetailClass = listPlanDetailClass;
    }
}
