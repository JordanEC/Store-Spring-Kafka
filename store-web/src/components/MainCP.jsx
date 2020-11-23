import React, { Component } from 'react';
import HomeCP from './HomeF'
import HeaderCP from './HeaderCP'
import FooterF from './FooterF'
import { Switch, Route, Redirect, withRouter } from 'react-router-dom'
import { connect } from 'react-redux'
import * as ItemActionCreators from '../redux/ItemActionCreators' 
import * as HeaderActionCreators from '../redux/HeaderActionCreators' 
import ItemListCP from './ItemListCP';
import ItemViewCP from './ItemViewCP';
import OrdersCP from './OrdersCP';

const mapStateToProps = (state, ownProps) => {
    return {
        storeAppReducers: state.StoreAppReducers,
        ownProps: ownProps
    }
}

const mapDispatchToProps = (dispatch) => ({
    fetchItems: () => {dispatch(ItemActionCreators.fetchItems())},
    getItem: (itemId) => {dispatch(ItemActionCreators.getItem(itemId))},
    createItem: (item) => {dispatch(ItemActionCreators.createItem(item))},
    updateItem: (item) => {dispatch(ItemActionCreators.updateItem(item))},
    initializeItem: (item) => {dispatch(ItemActionCreators.initializeItem(item))},
    deleteItem: (itemId) => {dispatch(ItemActionCreators.deleteItem(itemId))},
    
    doLoading: () => {dispatch(HeaderActionCreators.doLoading())},
    resetMessages: () => {dispatch(HeaderActionCreators.resetMessages())}
    //...
})

class MainCP extends Component {
    render() { 
        const ItemWithId = ({match}) => {
            return (
                <ItemViewCP 
                    getItem={this.props.getItem}
                    createItem={this.props.createItem}
                    updateItem={this.props.updateItem}
                    initializeItem={this.props.initializeItem}
                    itemId={match.params.itemId}
                    item={this.props.storeAppReducers.item}
                    history={this.props.ownProps.history}
                    isLoading={this.props.storeAppReducers.isLoading}
                    doLoading={this.props.doLoading}
                    errorMessage={this.props.storeAppReducers.errorMessage}
                    infoMessage={this.props.storeAppReducers.infoMessage}
                />
            )
        }

        return (
            <>
                <HeaderCP 
                    errorMessage={this.props.storeAppReducers.errorMessage}
                    infoMessage={this.props.storeAppReducers.infoMessage}
                    resetMessages={this.props.resetMessages}/>
                <Switch>
                    <Route path='/home' component={HomeCP} />
                    <Route exact path="/items/:itemId" component={ItemWithId} />
                    <Route path='/items' component={() => <ItemListCP 
                        items={this.props.storeAppReducers.items}
                        isLoading={this.props.storeAppReducers.isLoading}
                        resetMessages={this.props.resetMessages}
                        errorMessage={this.props.storeAppReducers.errorMessage}
                        history={this.props.ownProps.history}
                        doLoading={this.props.doLoading}
                        fetchItems={this.props.fetchItems}
                        deleteItem={this.props.deleteItem}
                        />}/>
                    <Route path='/orders' component={() => <OrdersCP />} />
                    <Redirect to='/home'/>
                </Switch>
                <FooterF/>
            </>
        )
    }
}

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(MainCP))
