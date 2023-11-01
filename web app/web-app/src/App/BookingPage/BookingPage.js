import React, { useCallback, useEffect, useState } from 'react';
import './BookingPage.scss';
import { connect } from 'react-redux';
import { PayPalScriptProvider, PayPalButtons } from '@paypal/react-paypal-js';

import {
  createBooking,
  checkout,
  updateOrderID,
} from '../../Store/Actions/booking.action';
import { Container, Form, Card } from 'react-bootstrap';
import AppLoading from '../AppLoading/AppLoading';
import AppNotification from '../AppNotification/AppNotification';
import { useHistory } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import { getParkingName } from '../../Store/Actions/parkingspace.action.js';

function BookingPage(props) {
  const [fromtime, setfromTime] = useState('');
  const [totime, settoTime] = useState('');
  const [userid, setUserid] = useState('');
  const [parkingspaceid, setparkingspaceid] = useState('');
  const [parkingspaceobject, setParkingSpaceObject] = useState('');
  const [show, setShow] = useState(false);
  const [isTransactionCompleted, setTransactionStatus] = useState(false);
  const [ErrorMessage, setErrorMessage] = useState('');
  const [order_ID, setOrderID] = useState('');
  const history = useHistory();
  const onSave = () => {
    setparkingspaceid(props.parkingName.id);
    setUserid(sessionStorage.getItem('id'));
    setParkingSpaceObject(props.parkingName);
    console.log('after fetching order id');
  };

  useEffect(() => {
    setparkingspaceid(sessionStorage.getItem('parkingspaceid'));
    setUserid(sessionStorage.getItem('id'));
    // setParkingSpaceObject(props.parkingName);
    // if (props.isBookingCreated) {
    //   history.push('/userbookingdetails');
    // }
    if (props.parkingName.length == 0) {
      props.getParkingName(sessionStorage.getItem('parkingspaceid'));
    }

    if (isTransactionCompleted) {
    }
  }, [props.parkingName]);

  const saveBooking = useCallback(
    (id) => {
      const bookingObject = {
        orderID: id,
        userId: sessionStorage.getItem('id'),
        fromDate: sessionStorage.getItem('fromtime'),
        toDate: sessionStorage.getItem('totime'),
        parkingSpaceId: sessionStorage.getItem('parkingspaceid'),
        parkingName: sessionStorage.getItem('parking_name'),
      };

      console.log('bookingObject');
      console.log(bookingObject);
      props.createBooking(bookingObject);
      history.push('/userbookingdetails');
    },
    [fromtime, totime]
  );

  const parkingspaceElements = (
    <div>
      <Card
        key={props.parkingName.id}
        style={{ width: '30rem' }}
        className='mb-md-4'
      >
        <Card.Body>
          <Card.Title>{props.parkingName.name}</Card.Title>
          <Card.Text>
            <h5>Details : {props.parkingName.parkingEntrance}</h5>
            <h5>Phone : {props.parkingName.phone}</h5>
            <h5>Address : {props.parkingName.address}</h5>
            <h5>Rate : ${props.parkingName.rate}</h5>
          </Card.Text>
        </Card.Body>
      </Card>
      <input
        type='hidden'
        id='parkingid'
        name='parkingid'
        value={props.parkingName.id}
      ></input>
    </div>
  );

  // creates a paypal order
  const createOrder = (data, actions) => {
    return actions.order
      .create({
        purchase_units: [
          {
            description: 'Test Booking',
            amount: {
              currency_code: 'USD',
              value: 15,
            },
          },
        ],
        // not needed if a shipping address is actually needed
        application_context: {
          shipping_preference: 'NO_SHIPPING',
        },
      })
      .then((orderID) => {
        //setOrderID(orderID);
        // console.log(order_ID);
        return orderID;
      });
  };

  const test = (e) => {
    console.log(`fromtime ${fromtime}`);
    console.log(`totime ${totime}`);
  };

  // check Approval
  const onApprove = useCallback(
    (data, actions) => {
      console.log(`fromtime ${fromtime}`);
      console.log(`totime ${totime}`);

      return actions.order.capture().then(function (details) {
        const { payer } = details;
        saveBooking(details.id);
      });
    },
    [saveBooking]
  );

  //capture likely error
  const onError = (data, actions) => {
    setErrorMessage('An Error occured with your payment ');
  };

  console.log(order_ID);

  if (props.isLoading) {
    return <AppLoading></AppLoading>;
  }

  return (
    <Container>
      <PayPalScriptProvider
        options={{
          'client-id':
            'Ac0kYTk8CFuDfRf2g0WbgrOu9ODPkICnhAoAIEa8oKaX25rbIVrcvfbU40h1x3_H5EsTDEJo4tzLb5ef',
        }}
      >
        <div className='page-header'>
          <h4>Booking Page</h4>
          <h3>{parkingspaceElements}</h3>

          <AppNotification error={props.error}></AppNotification>
          {/* {props.error && <div>{props.error.message}</div>} */}
        </div>
        <Form className='form-signup' autoComplete='off'>
          <Form.Group className='mb-3' controlId='formBasicInput'>
            <Form.Label>From Time</Form.Label>
            <Form.Control
              type='datetime-local'
              placeholder='From Time'
              value={fromtime}
              onChange={(e) => {
                sessionStorage.setItem('fromtime', e.target.value);
                setfromTime(e.target.value);
              }}
            ></Form.Control>
          </Form.Group>

          <Form.Group className='mb-3' controlId='formBasicInput'>
            <Form.Label>To Time:</Form.Label>
            <Form.Control
              type='datetime-local'
              placeholder='Enter to time'
              value={totime}
              onChange={(e) => {
                settoTime(e.target.value);
                sessionStorage.setItem('totime', e.target.value);
              }}
            ></Form.Control>
          </Form.Group>

          {/* <button variant='light' className='btn-signup-submit' onClick={test}>
            Book
          </button> */}
        </Form>
        <br></br>
        <div>
          <i>* No Cancellation: This booking will be recorded immediately</i>
        </div>
        <PayPalButtons
          style={{ layout: 'horizontal' }}
          createOrder={createOrder}
          onApprove={onApprove}
        />
      </PayPalScriptProvider>
    </Container>
  );
}

//add the state data to local props for easy access
function mapStateToProps(state, ownProps) {
  return {
    // isLoading: state.signup.isLoading,
    parkingspaceList: state.parkingspace.parkingspaceList,
    isBookingCreated: state.bookings.isBookingCreated,
    parkingName: state.parkingspace.parkingName,
    isLoading: state.bookings.isLoading,
    // error: state.signup.error,
    // isSignedUp: state.signup.isSignedUp,
    isOrderIdGenerated: state.bookings.isOrderIdGenerated,
  };
}

//maping createUser to props
const mapDispatchToProps = {
  getParkingName: getParkingName,
  createBooking: createBooking,
  checkout: checkout,
  updateOrderID: updateOrderID,
};

//connect injects dispatch in every object of mapDispatchToProps
const ConnectedSignup = connect(
  mapStateToProps,
  mapDispatchToProps
)(BookingPage);
export default ConnectedSignup;
