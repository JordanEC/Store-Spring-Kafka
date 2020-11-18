import React, { Component } from 'react';
import { Button, Col, Form, Row } from 'react-bootstrap';
import { Control, Errors, LocalForm } from 'react-redux-form';
import * as Validators from '../util/Validators'
import LoadingF from './LoadingF'

const handleSubmitItem = (props, values) => {
    props.item.name = values.name;
    props.item.description = values.description;
    props.item.unitPrice = values.unitPrice;

    if (props.item.itemId === undefined) {
        props.createItem(props.item)
    } else {
        props.updateItem(props.item)
    }
    
}

const getInitialState = (item) => { 
    return {
        name: item.name,
        description: item.description,
        unitPrice: item.unitPrice
    }
}

class ItemViewCP extends Component {
    constructor(props){
        super(props)
        if (this.props.infoMessage) {
            this.props.history.push('/items')
        } else if (this.props.itemId === 'new' && this.props.item === null && !this.props.errorMessage) {
            props.initializeItem({name:'', description:'',unitPrice:''})
        }
    }

    render() {
        if (this.props.item === null && this.props.itemId !== 'new' && !this.props.infoMessage) {
            if (!this.props.isLoading) {
                this.props.doLoading()
                this.props.getItem(this.props.itemId)
            }
            return(<LoadingF />)
        } else if (this.props.item !== null) {
            return (
                <div className="container">
                    <LocalForm 
                        onSubmit={(values)=>handleSubmitItem(this.props, values)}
                        initialState={getInitialState(this.props.item)} >
                        <Row className="form-group">
                            <Form.Label htmlFor="name">
                                Name:
                            </Form.Label>
                            <Control.text 
                                className="form-control"
                                name="name"
                                model=".name"
                                validators={{
                                    required: Validators.required,
                                    minLength: Validators.minLength(3)
                                }}/>
                                <Errors className="text-danger"
                                    model=".name"
                                    show="touched"
                                    messages={{
                                        required: 'Required. ',
                                        minLength: 'Must be greater than 2. '
                                    }}
                                >

                                </Errors>
                        </Row>
                        <Row className="form-group">
                            <Form.Label htmlFor="description">
                                Description:
                            </Form.Label>
                            <Control.textarea 
                                className="form-control"
                                name="description"
                                model=".description">
                            </Control.textarea>
                        </Row>
                        <Row className="form-group">
                            <Form.Label htmlFor="unitPrice">
                                Unit Price:
                            </Form.Label>
                            <Control.text 
                                className="form-control"
                                name="unitPrice"
                                model=".unitPrice">
                            </Control.text>
                        </Row>
                        <Row className="form-group">
                            <Col md={1}>
                                <Button type="submit" color="primary">Save</Button>
                            </Col>
                        </Row>                    
                    </LocalForm>
                </div>
            )
        } else {
            return null
        }
    }
}

export default ItemViewCP