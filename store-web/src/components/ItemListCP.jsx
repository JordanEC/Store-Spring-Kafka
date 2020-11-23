import React, { Component, useState } from 'react';
import { Button, Modal, Table } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import LoadingF from './LoadingF';
import BootstrapTable from 'react-bootstrap-table-next';
import paginationFactory, { PaginationListStandalone, PaginationTotalStandalone, PaginationProvider, SizePerPageDropdownStandalone } from 'react-bootstrap-table2-paginator';
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';

const addItem = (props) => {
    props.resetMessages()
    props.history.push('/items/new')
}

const DeleteModal = (props) => {
    const [show, setShow] = useState(false);

    const handleClose = () => {
        setShow(false)
    };
    const handleShow = () => {
        setShow(true)
    }
    
    const handleDelete = () => {
        props.deleteItem(props.itemId)
        setShow(false)
    }

    return (
        <>
            <Button className="btn btn-light bg-transparent border-0" onClick={handleShow}><span onClick={handleShow} className="fa fa-trash mr-1 fa-lg"/></Button>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton className="DeleteModal">
                <Modal.Title>Delete Item</Modal.Title>
                </Modal.Header>
                <Modal.Body className="DeleteModal">Are you sure you want to delete this item?</Modal.Body>
                <Modal.Footer className="DeleteModal">
                <Button variant="secondary" onClick={handleClose}>
                    No
                </Button>
                <Button variant="primary" onClick={handleDelete}>
                    Yes
                </Button>
                </Modal.Footer>
            </Modal>
        </>
    )
}


const ItemListCP = (props) => {
    if (props.errorMessage !== null)
    {
        return null
    } else if (props.items === null)
    {
        if (!props.isLoading)
        {
            props.doLoading()
            props.fetchItems()
        }
        return(<LoadingF />)
    } else {
        const { SearchBar } = Search;
        const customTotal = (from, to, size) => (
            <span className="react-bootstrap-table-pagination-total">
              Showing { from } to { to } of { size } Results
            </span>
          );
        const options = {
            custom: true,
            showTotal: true,
            totalSize: props.items.length,
            paginationTotalRenderer: customTotal,
            hideSizePerPage: false,
            sizePerPageList: [ 
                {
                    text: '10', 
                    value: 10
                },
                {
                    text: '20', 
                    value: 20
                },                
                {
                    text: 'All', 
                    value: props.items.length
                }
            ]          
          };
        const itemsTableColumns = [
            {
                dataField: 'itemId',
                text: 'Update/Delete',
                formatter: (cellContent, row) => (
                    <>
                        <Link to={'/items/'+row.itemId} onClick={() => props.resetMessages()}><span className="fa fa-pencil mr-1 fa-lg"/></Link>
                        <DeleteModal itemId={row.itemId} deleteItem={props.deleteItem} fetchItems={props.fetchItems} resetMessages={props.resetMessages}/>
                    </>
                )
            },
            {
                dataField: 'name',
                text: 'Name',
                sort: true
            }, 
            {
                dataField: 'description',
                text: 'Description',
                sort: true
            }, 
            {
                dataField: 'unitPrice',
                text: 'Unit Price',
                sort: true
            }
        ]

        const itemsTable = ({ paginationProps, paginationTableProps }) => (
            <div className="content itemsTable">
                <ToolkitProvider
                        bootstrap4
                        search
                        keyField='itemId' 
                        data={ props.items } 
                        columns={ itemsTableColumns }>
                    {
                        toolkitprops => (
                            <div>
                                <div className="row">
                                    <div className="col-6 col-md-3">
                                        <Button className="btn btn-light" onClick={()=>addItem(props)}>
                                            <span className="fa fa-pencil mr-1 fa-lg">Add Item</span>
                                        </Button>
                                    </div>
                                    <div className="col-6 col-md-3 offset-md-6">
                                        <SearchBar { ...toolkitprops.searchProps } />
                                    </div>
                                </div> 
                                <BootstrapTable
                                    
                                    hover
                                    bordered={false}
                                    noDataIndication="No items found"                 
                                    headerWrapperClasses="tableHeader"                                    
                                    { ...toolkitprops.baseProps }
                                    { ...paginationTableProps }
                                />                   
                            </div>
                        )                    
                    }
                </ToolkitProvider>
                <div className="row d-flex justify-content-center justify-content-md-between">
                    <div className="col-6 col-md-3 d-flex justify-content-end justify-content-md-start">
                        <SizePerPageDropdownStandalone { ...paginationProps }/>
                    </div>
                    <div className="col-6 col-md-3 d-flex justify-content-start justify-content-md-center">
                        <PaginationListStandalone { ...paginationProps } />
                    </div>
                    <div className="col-12 col-md-3 d-flex justify-content-center">
                        <PaginationTotalStandalone { ...paginationProps } />
                    </div>
                </div>
            </div>
        )
        return (
            <div className="overflow-auto container">
                <h1>Items</h1>
                
                <PaginationProvider pagination={ paginationFactory(options) }>
                    {itemsTable}
                </PaginationProvider>
            </div>
        )
    }
}

export default ItemListCP