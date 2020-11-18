import React, { Component } from 'react';
import { Alert, Image, Nav, Navbar } from 'react-bootstrap';
import { Link, NavLink } from 'react-router-dom';


function AlertCP(props) {

    if (props.errorMessage) {
        return (
            <Alert 
                dismissible="true" key="danger" variant="danger" 
                onClose={() => {props.resetMessages()}}>
                {props.errorMessage}
            </Alert>
        )
    } else if (props.infoMessage) {
        return (
            <Alert dismissible="true" key="success" variant="success" onClose={() => props.resetMessages()}>
                {props.infoMessage}
            </Alert>
        )
    } else {
        return null
    }
}

class HeaderCP extends Component { 
    constructor(props){
        super(props)
    }

    render() { 
        return (
            <>
                <Navbar bg="dark" variant="dark" expand="md">
                    <Navbar.Brand>
                    <Link to='/home'>
                        <Image src="../assets/images/store-icon192.png" width="50" height="50" />
                    </Link>
                    </Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="mr-auto">
                            <NavLink to='/items' className="mr-2">Items</NavLink>
                            <NavLink to='/orders' className="mr-2">Orders</NavLink>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
                <AlertCP 
                    errorMessage={this.props.errorMessage} 
                    infoMessage={this.props.infoMessage}
                    resetMessages={this.props.resetMessages}/>
            </>
        )
    }
}

export default HeaderCP