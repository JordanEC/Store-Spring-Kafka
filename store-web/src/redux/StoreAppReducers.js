import * as ActionTypes from './ActionTypes'

export const StoreAppReducers = (state = {
    isLoading: false,
    infoMessage: null,
    errorMessage:null,
    items: null,
    item: null,
    itemId: null
}, action) => {
    switch(action.type) {
        case ActionTypes.POPULATE_ITEMS:
            return {...state, errorMessage: null, items: action.payload, item: null, itemId: null, isLoading: false}
        case ActionTypes.POPULATE_ITEM:
                return {...state, errorMessage: null, items: null, item: action.payload, isLoading: false}
        case ActionTypes.INITIALIZE_ITEM:
            return {...state, errorMessage: null, items: null, item: action.payload, itemId: null, isLoading: false}          
        case ActionTypes.SHOW_ERROR:
            return {...state, errorMessage: action.payload, items: null, isLoading: false}
        case ActionTypes.CHANGE_SUCCESSFUL:
            return {...state, errorMessage: null, infoMessage: action.payload, items: null, item: null, itemId: null, isLoading: false}   
        case ActionTypes.RESET_MESSAGES:
            return {...state, errorMessage: null, infoMessage: null, isLoading: false}
        case ActionTypes.DO_LOADING:
            return {...state, isLoading: true}
        default:
            return state
    }
}