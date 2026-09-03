public with sharing class SoqlInjection{


    public List<Account> searchAccounts(String userSuppliedName) {
        String query =
            'SELECT Id, Name FROM Account WHERE Name = \'' + userSuppliedName + '\'';
        return Database.query(query);
    }


    public List<Contact> searchContacts(String lastName) {
        String where = 'LastName = \'' + lastName + '\'';
        String query = 'SELECT Id FROM Contact WHERE ' + where;
        return Database.query(query);
    }


    public Integer countByCity(String city) {
        return Database.countQuery(
            'SELECT COUNT() FROM Account WHERE BillingCity = \'' + city + '\'');
    }

    public Database.QueryLocator locatorFor(String status) {
        return Database.getQueryLocator(
            'SELECT Id FROM Case WHERE Status = \'' + status + '\'');
    }

    public List<Account> sortedAccounts(String orderField) {
        return Database.query('SELECT Id, Name FROM Account ORDER BY ' + orderField);
    }
}