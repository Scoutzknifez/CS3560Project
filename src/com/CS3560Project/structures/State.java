package com.CS3560Project.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum State {
    ALABAMA("AL"),
    ALASKA("AK"),
    AMERICAN_SAMOA("AS"),
    ARIZONA("AZ"),
    ARKANSAS("AR"),
    CALIFORNIA("CA"),
    COLORADO("CO"),
    CONNECTICUT("CT"),
    DELAWARE("DE"),
    DISTRICT_OF_COLUMBIA("DC"),
    FEDERATED_STATES_OF_MICRONESIA("FM"),
    FLORIDA("FL"),
    GEORGIA("GA"),
    GUAM("GU"),
    HAWAII("HI"),
    IDAHO("ID"),
    ILLINOIS("IL"),
    INDIANA("IN"),
    IOWA("IA"),
    KANSAS("KS"),
    KENTUCKY("KY"),
    LOUISIANA("LA"),
    MAINE("ME"),
    MARYLAND("MD"),
    MARSHALL_ISLANDS("MH"),
    MASSACHUSETTS("MA"),
    MICHIGAN("MI"),
    MINNESOTA("MN"),
    MISSISSIPPI("MS"),
    MISSOURI("MO"),
    MONTANA("MT"),
    NEBRASKA("NE"),
    NEVADA("NV"),
    NEW_HAMPSHIRE("NH"),
    NEW_JERSEY("NJ"),
    NEW_MEXICO("NM"),
    NEW_YORK("NY"),
    NORTH_CAROLINA("NC"),
    NORTH_DAKOTA("ND"),
    NORTHERN_MARIANA_ISLANDS("MP"),
    OHIO("OH"),
    OKLAHOMA("OK"),
    OREGON("OR"),
    PALAU("PW"),
    PENNSYLVANIA("PA"),
    PUERTO_RICO("PR"),
    RHODE_ISLAND("RI"),
    SOUTH_CAROLINA("SC"),
    SOUTH_DAKOTA("SD"),
    TENNESSEE("TN"),
    TEXAS("TX"),
    UNKNOWN("ERROR"),
    UTAH("UT"),
    VERMONT("VT"),
    VIRGIN_ISLANDS("VI"),
    VIRGINIA("VA"),
    WASHINGTON("WA"),
    WEST_VIRGINIA("WV"),
    WISCONSIN("WI"),
    WYOMING("WY");


    private String abbreviation;

    /**
     * Gets a state from a given abbreviation
     * @param abbreviation  Abbreviation of state to get
     * @return              State
     */
    public static State getStateFromAbbreviation(String abbreviation) {
        for (State state : State.values()) {
            if (state.getAbbreviation().equalsIgnoreCase(abbreviation))
                return state;
        }
        return UNKNOWN;
    }
}