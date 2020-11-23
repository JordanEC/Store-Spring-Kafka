import React, { Component } from 'react'
import { Button } from 'react-bootstrap'
import { Control, LocalForm } from 'react-redux-form'

class OrdersCP extends Component {
    constructor(props) {
        super(props)
    }

    handleOrderSearch = (values) => {
        console.log(values.toString())
    }

    render(){
        return (
            <div className="container">
                <h3>Orders</h3>
                <div className="row">
                    <LocalForm 
                        onSubmit={() => this.handleOrderSearch()}
                        // onUpdate={(form) => this.handleUpdate(form)}
                        // onChange={(values) => this.handleChange(values)}
                        >
                            <label>Order Number</label>
                            <Control.text model=".orderNumber" placeholder="Enter order number" />
                            {/* <Form.Text className="text-muted">
                            We'll never share your email with anyone else.
                            </Form.Text> */}

                        <Button variant="primary" type="submit">
                            Search
                        </Button>
                    </LocalForm>
                </div>
            </div>
        )
    }
}

export default OrdersCP