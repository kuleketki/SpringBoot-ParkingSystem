import React from 'react';
import axios from 'axios';
import { compose } from 'redux';

export const ActionType = {
  REQUEST_PARKINGSPACE_LIST: 'REQUEST_PARKING_LIST',
  REQUEST_PARKINGSPACE_SUCCESS: 'REQUEST_PARKINGSPACE_SUCCESS',

  REQUEST_PARKINGNAME: 'REQUEST_PARKINGNAME',
  REQUEST_PARKINGNAME_SUCCESS: 'REQUEST_PARKINGNAME_SUCCESS',

  CREATE_PARKING_SPACE: 'CREATE_PARKING_SPACE',
  CREATE_PARKING_SPACE_SUCCESS: 'CREATE_PARKING_SPACE_SUCCESS',
  CREATE_PARKING_SPACE_FAILURE: 'CREATE_PARKING_SPACE_FAILURE',
  UPDATE_PARKING_SPACE_SUCCESS_PARAMS: 'UPDATE_PARKING_SPACE_SUCCESS_PARAMS',
};

export const getParkingSpace = (req) => {
  return function (dispatch, getState) {
    const url = 'http://localhost:8080/search/' + req;

    axios.get(url).then((response) => {
      console.log('Response');
      console.log(response.data.data);
      sessionStorage.setItem(
        'parkingSpaceList',
        JSON.stringify(response.data.data)
      );
      // console.log(JSON.parse( sessionStorage.getItem("parkingSpaceList")));
      dispatch({
        type: ActionType.REQUEST_PARKINGSPACE_SUCCESS,
        parkingspaceList: response.data.data,
      });
    });
  };
};

export const getParkingName = (req) => {
  return function (dispatch, getState) {
    const url = 'http://localhost:8080/parkingsearch/' + req;

    axios.get(url).then((response) => {
      console.log(`parkingname${response.data.data.name}`);
      sessionStorage.setItem('parking_name', response.data.data.name);
      dispatch({
        type: ActionType.REQUEST_PARKINGNAME_SUCCESS,
        parkingName: response.data.data,
      });
    });
  };
};

export const createParkingSpace = (data) => {
  //thunk to avoid await and async
  return function (dispatch, getState) {
    //dispatch calls the reducer
    dispatch({
      type: ActionType.CREATE_PARKING_SPACE,
    });
    const url = 'http://localhost:8080/parkingspaces';
    axios
      .post(url, data)
      .then((response) => {
        console.log(response.data);

        setTimeout(() => {
          //creating a action of type CREATE_PARKING_SPACE_SUCCESS and payload as user and dispatching
          dispatch({
            type: ActionType.CREATE_PARKING_SPACE_SUCCESS,
            parkingspace: response.data,
          });
        }, 2000);
      })
      .catch((error) => {
        const e = {
          message: error.response.data.message,
          status: error.response.status,
        };
        setTimeout(() => {
          dispatch({
            type: ActionType.CREATE_PARKING_SPACE_FAILURE,
            error: e,
          });
        }, 2000);
      });
  };
};

export const updateSuccessParam = (isSuccess) => {
  return function (dispatch, getState) {
    dispatch({
      type: ActionType.UPDATE_PARKING_SPACE_SUCCESS_PARAMS,
      isAddedSuccessfully: isSuccess,
    });
  };
};
