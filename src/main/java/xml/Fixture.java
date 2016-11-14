package xml;

import java.util.List;

public class Fixture {
    public String nomeFixture;
    public List<SelectoresFixture> selectoresFixtures;

    public String usage;

    public String getNomeFixture() {
        return nomeFixture;
    }

    public void setNomeFixture(String nomeFixture) {
        this.nomeFixture = nomeFixture;
    }

    public List<SelectoresFixture> getSelectoresFixtures() {
        return selectoresFixtures;
    }

    public void setSelectoresFixtures(List<SelectoresFixture> selectoresFixtures) {
        this.selectoresFixtures = selectoresFixtures;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
}