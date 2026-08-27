
public with sharing class SoqlInjectionFixture {

    public List<Account> searchAccounts(String userSuppliedName) {
        String query =
            'SELECT Id, Name FROM Account WHERE Name = \'' + userSuppliedName + '\'';
        return Database.query(query);
    }


}
