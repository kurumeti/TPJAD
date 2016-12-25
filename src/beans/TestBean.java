package beans;


import javax.faces.bean.ManagedBean;
import java.io.Serializable;

@ManagedBean
public class TestBean implements Serializable{
  private static final long serialVersionUID = 1L;

  private String name = "";

  public TestBean() {
  }

  public TestBean(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
