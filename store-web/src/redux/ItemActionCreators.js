import * as ActionTypes from './ActionTypes'
import axios from 'axios'
import { PRODUCER_URL } from '../util/Constants' 
import * as HeaderActionCreators from './HeaderActionCreators'

export const fetchItems = () => (dispatch) => {
    //dispatch(HeaderActionCreators.doLoading())
    console.log('PRODUCER_URL='+PRODUCER_URL)
    axios.get(PRODUCER_URL + 'item'/*, {headers: {'Content-Type': 'application/json', 'Access-Control-Allow-Origin':'*'}}*/)
        .then((response) => {
            if (response.status === 200) {
                dispatch(populateItems(response.data))
            } else { 
                var error = new Error('Error '+ response.status + ': '+ response.statusText)
                error.response = response
                throw error
            }
        })
        .catch((error) => {
            dispatch(HeaderActionCreators.showError(error.message))
        })
}

export const getItem = (itemId) => (dispatch) => {
    //dispatch(HeaderActionCreators.doLoading())
    axios.get(PRODUCER_URL + 'item/' + itemId)
    .then((response) => {
        if (response.status === 200) {
            dispatch(populateItem(response.data))
        } else { 
            var error = new Error('Error '+ response.status + ': '+ response.statusText)
            error.response = response
            throw error
        }
    })
    .catch((error) => {
        dispatch(HeaderActionCreators.showError(error.message))
    })
}

export const createItem = (item) => (dispatch) => {
    //dispatch(HeaderActionCreators.doLoading())
    axios.post(PRODUCER_URL + 'item', item)
        .then((response) => {
            if (response.status !== 200) {
                var error = new Error('Error '+ response.status + ': '+ response.statusText)
                error.response = response
                throw error
            }
            dispatch(HeaderActionCreators.changeSuccessful('Item created'))
            //dispatch(fetchItems())
        })
        .catch((error) => {
            dispatch(HeaderActionCreators.showError(error.message))
        })
}

export const updateItem = (item) => (dispatch) => {
    //dispatch(HeaderActionCreators.doLoading())
    axios.put(PRODUCER_URL + `item/${item.itemId}`, item)
    .then((response) => {
        if (response.status !== 200) {
            var error = new Error('Error '+ response.status + ': '+ response.statusText)
            error.response = response
            throw error
        }
        dispatch(HeaderActionCreators.changeSuccessful('Item updated'))
        //dispatch(fetchItems())
    })
    .catch((error) => {
        dispatch(HeaderActionCreators.showError(error.message))
    })
}

export const deleteItem = (itemId) => (dispatch) => {
    //dispatch(HeaderActionCreators.doLoading())
    axios.delete(PRODUCER_URL + `item/${itemId}`)
    .then((response) => {
        if (response.status !== 200) {
            var error = new Error('Error '+ response.status + ': '+ response.statusText)
            error.response = response
            throw error
        }
        dispatch(HeaderActionCreators.changeSuccessful('Item deleted'))
        dispatch(fetchItems())
    })
    .catch((error) => {
        dispatch(HeaderActionCreators.showError(error.message))
    })
}

export const populateItems = (items) => ({
    type: ActionTypes.POPULATE_ITEMS,
    payload: items
})

export const populateItem = (item) => ({
    type: ActionTypes.POPULATE_ITEM,
    payload: item
})

export const initializeItem = (item) => ({
    type: ActionTypes.INITIALIZE_ITEM,
    payload: item
})


