<template>
  <span>
    <a href="" @click.stop.prevent="showSolution(rowData,index)"><svg-icon icon-class="link" /> 查看提交的代码</a>&nbsp;
    <a href="" @click.stop.prevent="update(rowData,index)"><svg-icon icon-class="edit" /> 编辑</a>&nbsp;
    <a href="" @click.stop.prevent="deleteRow(rowData,index)"><svg-icon icon-class="lock" /> 锁定</a>
  </span>
</template>

<script>
export default {
  name: 'UserOperationButtonWithSolution',
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
    showSolution() {
      const params = { type: 'showSolution', index: this.index, rowData: this.rowData }
      this.$emit('on-custom-comp', params)
    },
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
