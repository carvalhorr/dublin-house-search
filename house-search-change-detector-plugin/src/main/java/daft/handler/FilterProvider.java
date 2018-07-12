package daft.handler;

import daft.filter.*;
import daft.filter.boolean_expression.AndFilter;

public class FilterProvider implements IFilterProvider {

    private Filter filter = initFilter();

    @Override
    public Filter getFilters() {
        return filter;
    }

    private Filter initFilter() {
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
        filter.getFilters().add(smithfieldAndPortobeloArea);

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

        return filter;

    }

}
