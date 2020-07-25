<template>
  <span>
    <a href="" @click.stop.prevent="update(rowData,index)"><svg-icon icon-class="edit" /> 编辑</a>&nbsp;
    <a href="" @click.stop.prevent="deleteRow(rowData,index)"><svg-icon icon-class="lock" /> 锁定</a>
  </span>
</template>

<script>
export default {
  name: 'UserOperationButton',
  props: {
    rowData: {
      type: Object
    },
    field: {
      type: String
    },
    index: {
      type: Number
    }
  },
  methods: {
    update() {
      const params = { type: 'edit', index: this.index, rowData: this.rowData }
      this.$emit('on-custom-comp', params)
    },
    deleteRow() {
      const params = { type: 'delete', index: this.index }
      this.$emit('on-custom-comp', params)
    }
  }
}
</script>
