package daft.handler;

import daft.filter.*;
import daft.filter.boolean_expression.AndFilter;
import data.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FilterProvider implements ISearchProvider {

    private List<Search> searchers = initFilter();

    @Override
    public List<Search> getSearches() {
        return searchers;
    }

    private List<Search> initFilter() {

        AndFilter filter = new AndFilter();

        EqualsFilter houseShare = new EqualsFilter();
        houseShare.setFieldName("property_category");
        houseShare.setFieldType(ValueType.STRING);
        houseShare.setValue("sharing");
        filter.getFilters().add(houseShare);

        EqualsFilter notOwnerOccupied = new EqualsFilter();
        notOwnerOccupied.setFieldName("owner_occupied");
        notOwnerOccupied.setFieldType(ValueType.YESNO);
        notOwnerOccupied.setValue("no");
        filter.getFilters().add(notOwnerOccupied);

        InFilter notFemale = new InFilter();
        notFemale.setFieldName("gender");
        notFemale.setFieldType(ValueType.STRING);
        notFemale.setValue("male, either");
        filter.getFilters().add(notFemale);

        EqualsFilter smithfieldAndPortobeloArea = new EqualsFilter();
        smithfieldAndPortobeloArea.setFieldName("area");
        smithfieldAndPortobeloArea.setFieldType(ValueType.STRING);
        smithfieldAndPortobeloArea.setValue("Smithfield");
        // filter.getFilters().add(smithfieldAndPortobeloArea);

        ContainsFilter withWashingMachine = new ContainsFilter();
        withWashingMachine.setFieldName("facility");
        withWashingMachine.setFieldType(ValueType.STRING);
        withWashingMachine.setValue("Washing Machine");
        filter.getFilters().add(withWashingMachine);

        ContainsFilter withDishwasher = new ContainsFilter();
        withDishwasher.setFieldName("facility");
        withDishwasher.setFieldType(ValueType.STRING);
        withDishwasher.setValue("Dishwasher");
        filter.getFilters().add(withDishwasher);

        EqualsFilter doubleRoom = new EqualsFilter();
        doubleRoom.setFieldName("room_type");
        doubleRoom.setFieldType(ValueType.STRING);
        doubleRoom.setValue("double");
        filter.getFilters().add(doubleRoom);

        EqualsFilter leaseUnitIsMonths = new EqualsFilter();
        leaseUnitIsMonths.setFieldName("lease_units");
        leaseUnitIsMonths.setFieldType(ValueType.STRING);
        leaseUnitIsMonths.setValue("months");
        filter.getFilters().add(leaseUnitIsMonths);

        GreaterThanOrEqualFilter moreThan10Months = new GreaterThanOrEqualFilter();
        moreThan10Months.setFieldName("available_for");
        moreThan10Months.setFieldType(ValueType.INTEGER);
        moreThan10Months.setValue("10");
        filter.getFilters().add(moreThan10Months);

        LessThanOrEqualFilter priceLessThanOrEquals800 = new LessThanOrEqualFilter();
        priceLessThanOrEquals800.setFieldName("price");
        priceLessThanOrEquals800.setFieldType(ValueType.INTEGER);
        priceLessThanOrEquals800.setValue("800");
        filter.getFilters().add(priceLessThanOrEquals800);

        User user = new User("carvalhorr@gmail.com");
        user.setName("Rodrigo");
        user.setPhone("+353 0830315645");

        List<Action> actions = new LinkedList<Action>();
        SendEmailAction action = new SendEmailAction();
        // TODO Add the advertiser name in the message??
        // action.setMessage("Hi%2C%0A%0AI+am+looking+for+a+room+like+the+one+you+advertised.+I+work+Monday+to+Friday%2C+9h+to+18h+in+a+tech+company.+The+ideal+place+would+be+quiet+and+clean.+I+am+easygoing%2C+tidy%2C+very+social%2C+non-smoker%2C+single.+%0A%0APlease+reach+out+so+we+can+arrange+a+viewing.%0APhone%3A+%2B353+0830315645%0AEmail%3A+carvalhorr%40gmail.com%0A%0ARegards%2C%0ARodrigo");
        actions.add(action);

        Search search = new Search();
        search.setName("Houseshare in Smithfield under 800");
        search.setFilter(filter);
        search.setUser(user);
        search.setActions(actions);

        return Arrays.asList(search);

    }

}
